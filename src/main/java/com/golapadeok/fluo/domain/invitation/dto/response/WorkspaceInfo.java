package com.golapadeok.fluo.domain.invitation.dto.response;

import com.golapadeok.fluo.common.util.DateUtils;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import lombok.*;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkspaceInfo {

    private String workspaceId;
    private String title;
    private String intro;
    private String imageUrl;
    private String createDate;
    private String updateDate;

    @Builder
    public WorkspaceInfo(String workspaceId, String title, String intro, String imageUrl, String createDate, String updateDate) {
        this.workspaceId = workspaceId;
        this.title = title;
        this.intro = intro;
        this.imageUrl = imageUrl;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public static WorkspaceInfo of (Workspace workspace) {
        return WorkspaceInfo.builder()
                .workspaceId(String.valueOf(workspace.getId()))
                .title(workspace.getTitle())
                .intro(workspace.getDescription())
                .imageUrl(workspace.getImageUrl())
                .createDate(DateUtils.dateFormatter(workspace.getCreateDate()))
                .updateDate(DateUtils.dateFormatter(workspace.getUpdateDate()))
                .build();
    }
}
