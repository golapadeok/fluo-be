package com.golapadeok.fluo.domain.social.domain.coderedirect;

import com.golapadeok.fluo.domain.social.domain.SocialType;

public interface SocialOAuthRedirect {

    public SocialType getSocialType();

    /**
     * 각 소셜 페이지로 redirect할 URL을 build한다.
     * 사용자로부터 로그인 요청을 받아 소셜 로그인 서버 인증용 코드를 요청
     * 차후 다양한 로그인을 구현할 수 있도록 인터페이스로 생성
     */
    public String getOAuthRedirectURL();

}
