package com.golapadeok.fluo.domain.role.exception;

import lombok.Getter;

@Getter
public enum RoleErrorStatus {

    NOT_FOUND_WORKSPACE(400, "존재하지 않는 워크스페이스 입니다."),
    NOT_FOUND_ROLE(400, "존재하지 않는 역할 입니다."),
    DUPLICATION_NAME(400, "중복된 역할 이름 있습니다.");

    private final int status;
    private final String message;

    RoleErrorStatus(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
