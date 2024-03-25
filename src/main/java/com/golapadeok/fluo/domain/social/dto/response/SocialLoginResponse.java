package com.golapadeok.fluo.domain.social.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SocialLoginResponse {

    private String memberId;
    private String accessToken;
    private String refreshToken;

    @Builder
    public SocialLoginResponse(String memberId, String accessToken, String refreshToken) {
        this.memberId = memberId;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
