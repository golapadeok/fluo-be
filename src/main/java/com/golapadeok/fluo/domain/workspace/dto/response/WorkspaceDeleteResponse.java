package com.golapadeok.fluo.domain.workspace.dto.response;

import lombok.Getter;

@Getter
public class WorkspaceDeleteResponse {
    private final String message;

    private WorkspaceDeleteResponse(String message) {
        this.message = message;
    }

    public static WorkspaceDeleteResponse of(String message) {
        return new WorkspaceDeleteResponse(message);
    }
}
