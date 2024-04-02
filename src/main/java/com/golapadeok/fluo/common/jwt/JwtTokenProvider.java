package com.golapadeok.fluo.common.jwt;

import com.golapadeok.fluo.common.jwt.exception.JwtErrorException;
import io.jsonwebtoken.*;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
public class JwtTokenProvider {

    @Value("${jwt.secretKey}")
    private String secretKey;

//    private static final long accessTokenExpiredTime = 1000L * 60L * 60L * 24L; // 1일
//    private static final long refreshTokenExpiredTime = 1000L * 60L * 60L * 24L * 14L; // 2주
    private final long accessTokenExpiredTime = 1000L * 30L;
    private final long refreshTokenExpiredTime = 1000L * 60L;
    private final String authorization = "Authorization";
    private final String tokenPrefix = "Bearer ";
    private final String refreshToken = "RefreshToken";

    @PostConstruct
    private void secretKeyEncode() {
        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public Map<String, Object> getHeader() {
        Map<String, Object> params = new HashMap<>();
        params.put("alg", "HS256");
        params.put("typ", "JWT");
        return params;
    }

    // 엑세스 토큰 생성
    public String createAccessToken(String email) {
        Claims claims = Jwts.claims().setSubject("ACCESS_TOKEN");
        claims.put("email", email);
        Date now = new Date();

        return Jwts.builder()
                .setHeader(this.getHeader())
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiredTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // 리프레시 토큰 생성
    public String createRefreshToken() {
        Claims claims = Jwts.claims().setSubject("REFRESH_TOKEN");
        Date now = new Date();

        return Jwts.builder()
                .setHeader(this.getHeader())
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenExpiredTime))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    // 토큰의 만료시간 검증
    public boolean isTokenValidate(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(this.secretKey)
                    .build()
                    .parseClaimsJws(token);

            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (ExpiredJwtException e) {
            return false;
        }
    }

    // 엑세스 토큰 헤더에서 추출
    public Optional<String> extractAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(this.authorization).replace(this.tokenPrefix, ""));
    }

    public Optional<String> extractAccessToken(HttpServletResponse response) {
        return Optional.ofNullable(response.getHeader(this.authorization).replace(this.tokenPrefix, ""));
    }

//    // 리프레시 토큰 헤더에서 추출
//    public Optional<String> extractRefreshToken(HttpServletRequest request) {
//        return Optional.ofNullable(request.getHeader(this.refreshToken));
//    }

    // 엑세스 토큰에서 email 뽑기
    public Optional<String> extractEmail(String accessToken) {
        return Optional.ofNullable(Jwts.parserBuilder()
                .setSigningKey(this.secretKey)
                .build()
                .parseClaimsJws(accessToken)
                .getBody()
                .get("email", String.class));
    }
    
    // header로 새로 발급된 엑세스 토큰 전송
    public void sendAccessToken(HttpServletResponse response, String updateAccessToken) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader(HttpHeaders.AUTHORIZATION, this.tokenPrefix+updateAccessToken);
    }

    // 쿠키에서 refreshToken 찾기
    public Optional<String> extractRefreshTokenFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("refreshToken")) {
                    return Optional.ofNullable(cookie.getValue());
                }
            }
        }
        return Optional.empty();
    }
    
}
