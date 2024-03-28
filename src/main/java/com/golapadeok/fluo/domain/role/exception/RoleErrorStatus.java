package com.golapadeok.fluo.domain.role.exception;

import lombok.Getter;

@Getter
public enum RoleErrorStatus {

    NOT_FOUND_WORKSPACE(400, "존재하지 않는 워크스페이스 입니다."),
    NOT_FOUND_ROLE(400, "존재하지 않는 역할 입니다."),
    DUPLICATION_NAME(400, "중복된 역할 이름 있습니다."),
    LIMIT_EXCEEDED_FOR_CREATION(400, "생성할 수 있는 역할 개수를 초과했습니다."),
    NOT_UPDATE_ADMIN_ROLE(400, "관리자 역할을 수정할 수 없습니다."),
    NOT_DELETE_ADMIN_ROLE(400, "관리자 역할을 삭제할 수 없습니다.");

    private final int status;
    private final String message;

    RoleErrorStatus(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
