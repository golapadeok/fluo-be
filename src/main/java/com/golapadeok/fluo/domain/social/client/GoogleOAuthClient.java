package com.golapadeok.fluo.domain.social.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.golapadeok.fluo.domain.member.domain.Member;
import com.golapadeok.fluo.domain.social.dto.request.GoogleOAuthToken;
import com.golapadeok.fluo.domain.social.dto.response.GoogleMemberResponse;
import com.golapadeok.fluo.domain.social.record.GoogleOauthConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class GoogleOAuthClient implements SocialOauth{

    private final GoogleOauthConfig googleOauthConfig;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    @Override
    public String getOAuthRedirectURL() {
        return UriComponentsBuilder
                .fromHttpUrl("https://accounts.google.com/o/oauth2/v2/auth")
                .queryParam("response_type", "code")
                .queryParam("client_id", googleOauthConfig.clientId())
                .queryParam("redirect_uri", googleOauthConfig.redirectUri())
                .queryParam("scope", googleOauthConfig.scope())
                .toUriString();
    }

    @Override
    public ResponseEntity<String> requestAccessToken(String code) {
        String requestUrl = "https://oauth2.googleapis.com/token";
        Map<String, Object> params = new HashMap<>();

        params.put("code", code);
        params.put("client_id", googleOauthConfig.clientId());
        params.put("client_secret", googleOauthConfig.clientSecret());
        params.put("redirect_uri", googleOauthConfig.redirectUri());
        params.put("grant_type", "authorization_code");

        ResponseEntity<String> responseEntity = this.restTemplate.postForEntity(requestUrl, params, String.class);

        if(responseEntity.getStatusCode() == HttpStatus.OK) {
            return responseEntity;
        }

        return null;
    }

    // 일회성 코드 -> 엑세스 토큰
    public GoogleOAuthToken getAccessToken(ResponseEntity<String> response) throws JsonProcessingException {
        return this.objectMapper.readValue(response.getBody(), GoogleOAuthToken.class);
    }


    // googleOAuthToken을 통하여 파싱
    public Member requestUserInfo(GoogleOAuthToken googleOAuthToken) throws JsonProcessingException {
        String requestUrl = "https://www.googleapis.com/oauth2/v1/userinfo";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "+googleOAuthToken.getAccess_token());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = this.restTemplate.exchange(requestUrl, HttpMethod.GET, request, String.class);

        GoogleMemberResponse googleMemberResponse = this.objectMapper.readValue(response.getBody(), GoogleMemberResponse.class);
        return googleMemberResponse.toMember();
    }


}
