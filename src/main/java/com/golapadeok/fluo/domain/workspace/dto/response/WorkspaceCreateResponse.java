package com.golapadeok.fluo.domain.workspace.dto.response;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class WorkspaceCreateResponse {
    private final String workspaceId;
    private final String title;
    private final String description;
    private final LocalDate createDate;

    private WorkspaceCreateResponse(String workspaceId, String title, String description, LocalDate createDate) {
        this.workspaceId = workspaceId;
        this.title = title;
        this.description = description;
        this.createDate = createDate;
    }

    public static WorkspaceCreateResponse of(String workspaceId, String title, String description, LocalDate createDate) {
        return new WorkspaceCreateResponse(
                workspaceId,
                title,
                description,
                createDate
        );
    }
}
