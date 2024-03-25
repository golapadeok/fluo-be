package com.golapadeok.fluo.domain.social.domain.coderedirect;

import com.golapadeok.fluo.domain.social.domain.SocialType;
import com.golapadeok.fluo.domain.social.record.NaverOauthConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@Component
public class NaverOAuthRedirect implements SocialOAuthRedirect {

    private final NaverOauthConfig naverOauthConfig;

    @Override
    public SocialType getSocialType() {
        return SocialType.NAVER;
    }

    // https://developers.naver.com/docs/login/api/api.md
    @Override
    public String getOAuthRedirectURL() {
        return UriComponentsBuilder
                .fromUriString("https://nid.naver.com/oauth2.0/authorize")
                .queryParam("response_type", "code")
                .queryParam("client_id", naverOauthConfig.clientId())
                .queryParam("redirect_uri", naverOauthConfig.redirectUri())
                .queryParam("scope", naverOauthConfig.scope())
                .queryParam("state", "samplestate")
                .toUriString();
    }
}
