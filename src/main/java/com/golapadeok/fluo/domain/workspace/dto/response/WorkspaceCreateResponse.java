package com.golapadeok.fluo.domain.workspace.dto.response;

import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class WorkspaceCreateResponse {
    private final String workspaceId;
    private final String title;
    private final String description;
    private final String imageUrl;
    private final LocalDate createDate;

    private WorkspaceCreateResponse(String workspaceId, String title, String description, String imageUrl, LocalDate createDate) {
        this.workspaceId = workspaceId;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.createDate = createDate;
    }

    public static WorkspaceCreateResponse of(Workspace workspace) {
        return new WorkspaceCreateResponse(
                workspace.getId().toString(),
                workspace.getTitle(),
                workspace.getDescription(),
                workspace.getImageUrl(),
                workspace.getCreateDate().toLocalDate()
        );
    }
}
