package com.golapadeok.fluo.common.security.filter;

import com.golapadeok.fluo.common.jwt.JwtTokenProvider;
import com.golapadeok.fluo.common.security.domain.PrincipalDetails;
import com.golapadeok.fluo.domain.member.domain.Member;
import com.golapadeok.fluo.domain.member.repository.MemberRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

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
         * header에 refresh token이 오는 경우는 access token이 만료되어 재 발행을 요청할 때 오는 경우이다.
         * 그렇기 때문에 먼저 refresh token이 만료되었는지를 확인하고,
         * 만료되지 않았다면 db에 저장된 refresh token과 비교하여 access token을 재발행 해준다.
         *
         * 만료가 되었는지 안되었는지는 filter의 isTokenValidate를 통해 확인한다.
         * 만약 만료되지 않았다면 refreshToken에 값이 들어가고, 만료되었다면 null을 반환한다.
          */
        String refreshToken = this.provider.extractRefreshToken(request)
                .filter(this.provider::isTokenValidate)
                .orElse(null);

        // refresh token이 만료되지 않았다면 새로운 access token 을 만들고,
        if(refreshToken != null) {
            this.memberRepository.findByRefreshToken(refreshToken)
                    .ifPresent(member -> {
                        this.provider.sendAccessToken(response, this.provider.createAccessToken(member.getEmail()));
                    });
            return;
        }

        // access token 유효성 검사 후 인증 로직
        this.provider.extractAccessToken(request)
                .filter(this.provider::isTokenValidate)
                .flatMap(this.provider::extractEmail)
                .flatMap(this.memberRepository::findByEmail)
                .ifPresent(this::saveAuthentication);

        chain.doFilter(request, response);
    }

    private void saveAuthentication(Member member) {
        PrincipalDetails principalDetails = new PrincipalDetails(member);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(principalDetails, null, principalDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
