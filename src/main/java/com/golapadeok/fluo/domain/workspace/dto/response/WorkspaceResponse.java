package com.golapadeok.fluo.domain.workspace.dto.response;

import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import lombok.Getter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Getter
public class WorkspaceResponse {
    private final String workspaceId;
    private final String title;
    private final String description;
    private final String imageUrl;
    private final String createDate;

    public WorkspaceResponse(Workspace workspace) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US)
                .withZone(ZoneId.of("GMT"));

        this.workspaceId = workspace.getId().toString();
        this.title = workspace.getTitle();
        this.description = workspace.getDescription();
        this.imageUrl = workspace.getImageUrl();
        this.createDate = workspace.getCreateDate().format(formatter);
    }
}
