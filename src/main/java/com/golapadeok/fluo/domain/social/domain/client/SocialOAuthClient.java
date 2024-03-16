package com.golapadeok.fluo.domain.social.domain.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.golapadeok.fluo.domain.member.domain.Member;
import com.golapadeok.fluo.domain.social.domain.SocialType;
import com.golapadeok.fluo.domain.social.dto.request.SocialToken;
import org.springframework.http.ResponseEntity;

public interface SocialOAuthClient {

    public SocialType getSocialType();

    // 일회성 코드를 소셜로그인으로 보낸 후 access token이 담긴 객체를 반환
    public Member requestAccessTokenAndUserInfo(String code) throws JsonProcessingException;

}
