package com.golapadeok.fluo.domain.social.client;

import org.springframework.http.ResponseEntity;

public interface SocialOauth {

    /**
     * 각 소셜 페이지로 redirect할 URL을 build한다.
     * 사용자로부터 로그인 요청을 받아 소셜 로그인 서버 인증용 코드를 요청
     * 차후 다양한 로그인을 구현할 수 있도록 인터페이스로 생성
     */
    public String getOAuthRedirectURL();

    // 일회성 코드를 소셜로그인으로 보낸 후 access token이 담긴 객체를 반환
    public ResponseEntity<String> requestAccessToken(String code);

}
