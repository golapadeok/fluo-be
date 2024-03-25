package com.golapadeok.fluo.domain.member.dto.response;

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
    private List<WorkspaceWithMemberInfoResponse> members;
    private String createDate;
    private String updateDate;

    public static WorkspaceInfoResponse of (Workspace workspace, List<WorkspaceWithMemberInfoResponse> members) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US)
                .withZone(ZoneId.of("GMT"));

        return WorkspaceInfoResponse.builder()
                .workspaceId(String.valueOf(workspace.getId()))
                .title(workspace.getTitle())
                .intro(workspace.getDescription())
                .members(members)
                .createDate(workspace.getCreateDate().format(formatter))
                .updateDate(workspace.getCreateDate().format(formatter))
                .build();
    }

}
