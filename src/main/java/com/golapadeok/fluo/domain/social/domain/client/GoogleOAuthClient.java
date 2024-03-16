package com.golapadeok.fluo.domain.social.domain.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.golapadeok.fluo.domain.member.domain.Member;
import com.golapadeok.fluo.domain.social.domain.SocialType;
import com.golapadeok.fluo.domain.social.dto.request.GoogleOAuthToken;
import com.golapadeok.fluo.domain.social.dto.request.NaverOAuthToken;
import com.golapadeok.fluo.domain.social.dto.request.SocialToken;
import com.golapadeok.fluo.domain.social.dto.response.GoogleMemberResponse;
import com.golapadeok.fluo.domain.social.record.GoogleOauthConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class GoogleOAuthClient implements SocialOAuthClient {

    private final GoogleOauthConfig googleOauthConfig;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    @Override
    public SocialType getSocialType() {
        return SocialType.GOOGLE;
    }

    @Override
    public Member requestAccessTokenAndUserInfo(String code) throws JsonProcessingException {
        String requestUrl = "https://oauth2.googleapis.com/token";
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();

        params.add("code", code);
        params.add("client_id", googleOauthConfig.clientId());
        params.add("client_secret", googleOauthConfig.clientSecret());
        params.add("redirect_uri", googleOauthConfig.redirectUri());
        params.add("grant_type", "authorization_code");

        ResponseEntity<String> responseEntity = this.restTemplate.postForEntity(requestUrl, params, String.class);

        if(responseEntity.getStatusCode() != HttpStatus.OK) {
            return null;
        }

        GoogleOAuthToken googleOAuthToken = this.getAccessToken(responseEntity);

        return requestUserInfo(googleOAuthToken);
    }

    public GoogleOAuthToken getAccessToken(ResponseEntity<String> response) throws JsonProcessingException {
        return this.objectMapper.readValue(response.getBody(), GoogleOAuthToken.class);
    }

    // 소셜 로그인을 통해 유저 객체 반환
    public Member requestUserInfo(SocialToken socialToken) throws JsonProcessingException {
        String requestUrl = "https://www.googleapis.com/oauth2/v1/userinfo";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "+socialToken.getAccessToken());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = this.restTemplate.exchange(requestUrl, HttpMethod.GET, request, String.class);

        GoogleMemberResponse googleMemberResponse = this.objectMapper.readValue(response.getBody(), GoogleMemberResponse.class);
        return googleMemberResponse.toMember();
    }


}
