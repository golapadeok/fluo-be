package com.golapadeok.fluo.domain.social.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@NoArgsConstructor
public class NaverOAuthToken implements SocialToken{

    private String access_token;
    private String refresh_token;
    private String token_type;
    private Integer expries_in;
    private String error;
    private String error_description;

    @Override
    public String getAccessToken() {
        return this.access_token;
    }
}
