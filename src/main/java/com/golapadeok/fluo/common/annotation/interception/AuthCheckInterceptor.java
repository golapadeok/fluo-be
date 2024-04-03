package com.golapadeok.fluo.common.annotation.interception;

import com.golapadeok.fluo.common.annotation.AuthCheck;
import com.golapadeok.fluo.common.annotation.interception.exception.AuthException;
import com.golapadeok.fluo.common.annotation.interception.exception.AuthStatus;
import com.golapadeok.fluo.common.jwt.JwtTokenProvider;
import com.golapadeok.fluo.common.security.domain.PrincipalDetails;
import com.golapadeok.fluo.domain.member.domain.Member;
import com.golapadeok.fluo.domain.member.repository.MemberRepository;
import com.golapadeok.fluo.domain.role.domain.Credential;
import com.golapadeok.fluo.domain.role.domain.MemberRole;
import com.golapadeok.fluo.domain.role.repository.MemberRoleQueryRepository;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import com.golapadeok.fluo.domain.workspace.repository.WorkspaceRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Slf4j
@Component
public class AuthCheckInterceptor implements HandlerInterceptor {

    private final JwtTokenProvider provider;
    private final MemberRepository memberRepository;
    private final WorkspaceRepository workspaceRepository;
    private final MemberRoleQueryRepository memberRoleQueryRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.debug("---- [ {} API interceptor ] [ HttpMethod : {} ] ----", request.getRequestURL(), request.getMethod());
        log.info("access token : {}", request.getHeader(HttpHeaders.AUTHORIZATION));
        Optional<String> s = provider.extractRefreshTokenFromCookies(request);
        if(s.isPresent()) {
            log.info("refresh token : {}", provider.extractRefreshTokenFromCookies(request).get());
        }

        AuthCheck authCheck = annotationExtracted(handler);
        if(authCheck != null) {
//            if(provider.extractAccessTokenFromCookies(request).isEmpty()) {
//                return false;
//            }
            if(request.getHeader(HttpHeaders.AUTHORIZATION) == null) {
                return false;
            }

            // 어노테이션에 설정된 권한 가져오기
            Credential[] credentials = authCheck.credential();

            // SecurityContextHolder를 통해 API에 접근하는 회원의 정보를 가져온다.

            Member member = getAuthentication(request, response);
            log.info("preHandle_member : {}", member);


            // 쿠키에 저장된 워크스페이스 아이디를 가져온다.
            String workspaceId = getWorkspaceId(request);
            log.info("workspaceId : {}", workspaceId);

            // 워크스페이스 아이디가 존재하는지 검증
            Workspace workspace = this.workspaceRepository.findById(Long.valueOf(workspaceId))
                    .orElseThrow(() -> new AuthException(AuthStatus.NOT_FOUND_WORKSPACE));
            log.info("workspace : {}", workspace);

            // 워크스페이스 아이디와 멤버 아이디로 멤버의 역할을 조회
            MemberRole memberRole = this.memberRoleQueryRepository.findWorkspaceWithMemberRoleList(member.getId(), workspace.getId())
                    .orElseThrow(() -> new AuthException(AuthStatus.NOT_FOUND_ROLE));
            log.info("memberRole : {}", memberRole);

            Set<String> roleSet = new HashSet<>(memberRole.getRole().getRoleList());

            // 역할 내에 어노테이션이 설정된 권한과 일치하는지 검증
            boolean hasPermission = Arrays.stream(credentials)
                    .anyMatch(credential -> roleSet.contains(credential.toString()));

            // 일치하면 true 일치하지 않다면 예외 발생 (권한 없음)
            if(!hasPermission) {
                throw new AuthException(AuthStatus.NOT_PERMISSION);
            }
        }

        // 어노테이션이 안붙은 API는 그냥 통과
        return true;
    }

    private String getWorkspaceId(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("workspaceId")) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private Member getAuthentication(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null) {
            String email = authentication.getName();
            log.info("email : {}", email);
            return this.memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
        }
        return null;
    }

    private AuthCheck annotationExtracted(Object handler) {
        if(handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            return handlerMethod.getMethodAnnotation(AuthCheck.class);
        }
        return null;
    }
}
