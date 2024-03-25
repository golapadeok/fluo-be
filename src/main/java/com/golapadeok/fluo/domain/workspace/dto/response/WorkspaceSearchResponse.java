package com.golapadeok.fluo.domain.workspace.dto.response;

import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import lombok.Getter;

import java.time.LocalDate;

@Getter
//TODO KDY MEMBER 추가 필요
public class WorkspaceSearchResponse {
    private final String workspaceId;
    private final String title;
    private final String description;
    private final String imageUrl;
    private final LocalDate createDate;

    private WorkspaceSearchResponse(String workspaceId, String title, String description, String imageUrl, LocalDate createDate) {
        this.workspaceId = workspaceId;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.createDate = createDate;
    }

    public static WorkspaceSearchResponse of(Workspace workspace) {
        return new WorkspaceSearchResponse(
                workspace.getId().toString(),
                workspace.getTitle(),
                workspace.getDescription(),
                workspace.getImageUrl(),
                workspace.getCreateDate().toLocalDate()
        );
    }
}
