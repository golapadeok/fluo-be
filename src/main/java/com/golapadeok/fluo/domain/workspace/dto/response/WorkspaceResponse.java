package com.golapadeok.fluo.domain.workspace.dto.response;

import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class WorkspaceResponse {
    private final String workspaceId;
    private final String title;
    private final String description;
    private final String imageUrl;
    private final LocalDate createDate;

    public WorkspaceResponse(Workspace workspace) {
        this.workspaceId = workspace.getId().toString();
        this.title = workspace.getTitle();
        this.description = workspace.getDescription();
        this.imageUrl = workspace.getImageUrl();
        this.createDate = workspace.getCreateDate().toLocalDate();
    }
}
