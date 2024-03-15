package com.golapadeok.fluo.domain.social.record;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.oauth2.google")
public record GoogleOauthConfig(
    String clientId,
    String clientSecret,
    String redirectUri,
    String scope
) {
}
