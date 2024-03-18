package com.golapadeok.fluo.domain.workspace.exception;

import lombok.Getter;

@Getter
public enum WorkspaceStatus {
    NOT_FOUND_WORKSPACE(400, "존재하지 않는 워크스페이스 입니다.");

    private final int status;
    private final String message;

    WorkspaceStatus(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
