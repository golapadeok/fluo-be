package com.golapadeok.fluo.domain.workspace.dto.request;

import lombok.Getter;

@Getter
public class WorkspaceRequest {
    private final String title;
    private final String description;

    public WorkspaceRequest(String title, String description) {
        this.title = title;
        this.description = description;
    }
}
