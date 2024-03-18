package com.golapadeok.fluo.domain.social.exception;

import lombok.Getter;

@Getter
public enum SocialErrorStatus {

    NOT_FOUNT_SOCIAL_TYPE(400, "지원하지 않는 소셜 타입입니다."),
    NOT_FOUNT_SOCIAL_LOGIN(400, "지원하지 않는 소셜 로그인 입니다."),
    USER_ALREADY_LOGGED_OUT(403, "이미 로그아웃한 계정입니다."),
    NOT_FOUNT_USER(400, "존재하지 않는 회원입니다.");

    private final int status;
    private final String message;

    SocialErrorStatus(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
