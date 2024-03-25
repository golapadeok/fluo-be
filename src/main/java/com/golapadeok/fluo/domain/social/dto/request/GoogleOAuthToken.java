package com.golapadeok.fluo.domain.social.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GoogleOAuthToken implements SocialToken{
    // 구글에 일회성 코드를 다시 보내 받아올 엑세스 토큰을 포함한 JSON 문자열을 담을 클래스

    private String access_token;
    private int expires_in;
    private String scope;
    private String token_type;
    private String id_token;

    @Override
    public String getAccessToken() {
        return this.access_token;
    }
}
