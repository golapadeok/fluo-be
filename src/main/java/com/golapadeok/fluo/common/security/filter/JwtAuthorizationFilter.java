package com.golapadeok.fluo.common.security.filter;

import com.golapadeok.fluo.common.jwt.JwtErrorStatus;
import com.golapadeok.fluo.common.jwt.JwtTokenProvider;
import com.golapadeok.fluo.common.jwt.exception.JwtErrorException;
import com.golapadeok.fluo.common.security.domain.PrincipalDetails;
import com.golapadeok.fluo.domain.member.domain.Member;
import com.golapadeok.fluo.domain.member.repository.MemberRepository;
import com.golapadeok.fluo.domain.social.domain.BlackList;
import com.golapadeok.fluo.domain.social.repository.BlackListRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider provider;
    private final MemberRepository memberRepository;

    private final String authorization = "Authorization";
    private final String tokenPrefix = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        // 헤더에 Authorization이라는 이름이 있는지를 확인
        String header = request.getHeader(this.authorization);
        if(header == null || !header.startsWith(this.tokenPrefix)) {
            chain.doFilter(request, response);
            return;
        }

        /**
         * 쿠키에 저장된 refresh token을 꺼내와 만료되었는지를 확인
         * 만료되었다면 예외를 발생, 재로그인을 해야함. (만료된 토큰입니다.)
         * 만료가 되지 않았다면 access token의 만료를 확인
         * access token이 만료되었다면 refresh token이 만료되지 않았다는 기준하에 db에 저장된 refresh token과 대조하여 확인
         * db에 저장된 refresh token과 같다면 새로운 access token을 생성하고 헤더로 보낸다.
         * -> 새로 생성된 엑세스 토큰을 가지고 인증을 관리
         */
        String refreshToken = this.provider.extractRefreshTokenFromCookies(request)
                .filter(this.provider::isTokenValidate)
                .orElseThrow(() -> new JwtErrorException(JwtErrorStatus.EXPIRED_REFRESH));

        String accessToken = this.provider.extractAccessToken(request)
                .filter(this.provider::isTokenValidate)
                .orElse(null);

        // access token이 만료되었을 때 재발급해주는 로직
        if(accessToken == null) {
            this.memberRepository.findByRefreshToken(refreshToken)
                    .ifPresent(member -> {
                        this.provider.sendAccessToken(response, this.provider.createAccessToken(member.getEmail()));
                    });
            return;
        }

        // access token이 만료되지 않았을 때 유효성 검사 후 인증 로직
        try {
            this.provider.extractAccessToken(request)
                    .filter(this.provider::isTokenValidate)
                    .flatMap(this.provider::extractEmail)
                    .flatMap(this.memberRepository::findByEmail)
                    .ifPresent(this::saveAuthentication);

            chain.doFilter(request, response);
        } catch(SecurityException | MalformedJwtException e) {
            log.error("MalformedJwtException 에러남");
            throw new JwtErrorException(JwtErrorStatus.MALFORMED_JWT); // 잘못된 JWT 서명입니다.
        } catch (ExpiredJwtException e) {
            log.error("ExpiredJwtException 에러남");
            throw new JwtErrorException(JwtErrorStatus.EXPIRED_JWT); // 만료된 JWT 토큰입니다.
        }
    }

    private void saveAuthentication(Member member) {
        PrincipalDetails principalDetails = new PrincipalDetails(member);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
