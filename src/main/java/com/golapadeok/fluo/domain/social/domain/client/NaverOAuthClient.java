package com.golapadeok.fluo.domain.social.domain.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.golapadeok.fluo.domain.member.domain.Member;
import com.golapadeok.fluo.domain.social.domain.SocialType;
import com.golapadeok.fluo.domain.social.dto.request.NaverOAuthToken;
import com.golapadeok.fluo.domain.social.dto.request.SocialToken;
import com.golapadeok.fluo.domain.social.dto.response.NaverMemberResponse;
import com.golapadeok.fluo.domain.social.record.NaverOauthConfig;
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
public class NaverOAuthClient implements SocialOAuthClient {


    private final NaverOauthConfig naverOauthConfig;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    @Override
    public SocialType getSocialType() {
        return SocialType.NAVER;
    }

    // 공식문서 : https://developers.naver.com/docs/login/api/api.md
    @Override
    public Member requestAccessTokenAndUserInfo(String code) throws JsonProcessingException {
        String requestUrl = "https://nid.naver.com/oauth2.0/token";
        MultiValueMap<String, Object> params = new LinkedMultiValueMap<>();

        params.add("grant_type","authorization_code");
        params.add("client_id", naverOauthConfig.clientId());
        params.add("client_secret", naverOauthConfig.clientSecret());
        params.add("code", code);
        params.add("state", "samplestate");

        ResponseEntity<String> responseEntity = this.restTemplate.postForEntity(requestUrl, params, String.class);

        if(responseEntity.getStatusCode() != HttpStatus.OK) {
            return null;
        }

        NaverOAuthToken naverOAuthToken = this.getAccessToken(responseEntity);

        return this.requestUserInfo(naverOAuthToken);
    }

    public NaverOAuthToken getAccessToken(ResponseEntity<String> response) throws JsonProcessingException {
        return this.objectMapper.readValue(response.getBody(), NaverOAuthToken.class);
    }

    // 공식문서 : https://developers.naver.com/docs/login/profile/profile.md
    public Member requestUserInfo(SocialToken socialToken) throws JsonProcessingException {
        String requestUrl = "https://openapi.naver.com/v1/nid/me";
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer "+socialToken.getAccessToken());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = this.restTemplate.exchange(requestUrl, HttpMethod.GET, request, String.class);

        NaverMemberResponse naverMemberResponse = this.objectMapper.readValue(response.getBody(), NaverMemberResponse.class);
        return naverMemberResponse.toMember();
    }
}
