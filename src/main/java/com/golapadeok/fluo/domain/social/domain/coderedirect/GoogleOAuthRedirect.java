package com.golapadeok.fluo.domain.social.domain.coderedirect;

import com.golapadeok.fluo.domain.social.domain.SocialType;
import com.golapadeok.fluo.domain.social.record.GoogleOauthConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@RequiredArgsConstructor
@Component
public class GoogleOAuthRedirect implements SocialOAuthRedirect {

    private final GoogleOauthConfig googleOauthConfig;

    @Override
    public SocialType getSocialType() {
        return SocialType.GOOGLE;
    }

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
}
