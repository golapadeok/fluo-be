package com.golapadeok.fluo.domain.member.dto.response;

import com.golapadeok.fluo.common.util.DateUtils;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@ToString
@Getter
@Builder
@AllArgsConstructor
public class WorkspaceInfoResponse {

    private String workspaceId;
    private String title;
    private String intro;
    private String imageUrl;
    private List<WorkspaceWithMemberInfoResponse> members;
    private String createDate;
    private String updateDate;

    public static WorkspaceInfoResponse of (Workspace workspace, List<WorkspaceWithMemberInfoResponse> members) {
        return WorkspaceInfoResponse.builder()
                .workspaceId(String.valueOf(workspace.getId()))
                .title(workspace.getTitle())
                .intro(workspace.getDescription())
                .imageUrl(workspace.getImageUrl())
                .members(members)
                .createDate(DateUtils.dateFormatter(workspace.getCreateDate()))
                .updateDate(DateUtils.dateFormatter(workspace.getUpdateDate()))
                .build();
    }

}
