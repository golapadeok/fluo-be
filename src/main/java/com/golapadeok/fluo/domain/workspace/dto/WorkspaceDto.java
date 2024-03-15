package com.golapadeok.fluo.domain.workspace.dto;

import lombok.Getter;

@Getter
public class WorkspaceDto {
    private final String workspaceId;
    private final String title;
    private final String description;

    public WorkspaceDto(String workspaceId, String title, String description) {
        this.workspaceId = workspaceId;
        this.title = title;
        this.description = description;
    }
}
