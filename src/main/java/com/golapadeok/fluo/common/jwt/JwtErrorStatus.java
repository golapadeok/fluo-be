package com.golapadeok.fluo.common.jwt;

import lombok.Getter;

@Getter
public enum JwtErrorStatus {

    MALFORMED_JWT(401, "잘못된 JWT 서명입니다."),
    EXPIRED_JWT(401, "만료된 JWT 토큰입니다.");

    private final int status;
    private final String message;

    JwtErrorStatus(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
