package com.golapadeok.fluo.domain.social.record;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "oauth2.naver")
public record NaverOauthConfig(
    String clientId,
    String clientSecret,
    String redirectUri,
    String scope
) {
}
