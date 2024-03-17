package com.golapadeok.fluo.domain.workspace.exception;

import lombok.Getter;

@Getter
public class NotFoundWorkspaceException extends RuntimeException {
    private final WorkspaceStatus workspaceStatus;

    public NotFoundWorkspaceException() {
        this.workspaceStatus = WorkspaceStatus.NOT_FOUND_WORKSPACE;
    }
}
