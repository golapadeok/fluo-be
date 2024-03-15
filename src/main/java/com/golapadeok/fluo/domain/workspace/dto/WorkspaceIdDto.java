package com.golapadeok.fluo.domain.workspace.dto;

import lombok.Getter;

@Getter
public class WorkspaceIdDto {
    private final String workspaceId;

    public WorkspaceIdDto(String workspaceId) {
        this.workspaceId = workspaceId;
    }
}
