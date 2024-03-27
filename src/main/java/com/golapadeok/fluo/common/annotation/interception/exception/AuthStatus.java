package com.golapadeok.fluo.common.annotation.interception.exception;

import lombok.Getter;

@Getter
public enum AuthStatus {

    NOT_FOUND_WORKSPACE(400, "존재하지 않는 워크스페이스 입니다."),
    NOT_FOUND_ROLE(400, "역할이 없거나 소속되지 않았습니다."),
    NOT_PERMISSION(403, "권한이 없습니다.");

    private final int status;
    private final String message;

    AuthStatus(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
