package com.golapadeok.fluo.domain.workspace.dto.response;

import com.golapadeok.fluo.domain.member.domain.Member;
import com.golapadeok.fluo.domain.member.domain.WorkspaceMember;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import com.golapadeok.fluo.domain.workspace.dto.MemberDto;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class WorkspaceSearchWithMembersResponse {
    private final String workspaceId;
    private final String title;
    private final String description;
    private final String imageUrl;
    private final LocalDate createDate;
    private final List<MemberDto> members;

    private WorkspaceSearchWithMembersResponse(String workspaceId, String title, String description, String imageUrl, LocalDate createDate, List<MemberDto> members) {
        this.workspaceId = workspaceId;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.createDate = createDate;
        this.members = members;
    }

    public static WorkspaceSearchWithMembersResponse of(Workspace workspace) {
        return new WorkspaceSearchWithMembersResponse(
                workspace.getId().toString(),
                workspace.getTitle(),
                workspace.getDescription(),
                workspace.getImageUrl(),
                workspace.getCreateDate().toLocalDate(),
                MemberDto.of(getMembers(workspace))
        );
    }

    private static List<Member> getMembers(Workspace workspace) {
        List<WorkspaceMember> workspaceMembers = workspace.getWorkspaceMembers();
        return workspaceMembers.stream()
                .map(WorkspaceMember::getMember)
                .toList();
    }
}
