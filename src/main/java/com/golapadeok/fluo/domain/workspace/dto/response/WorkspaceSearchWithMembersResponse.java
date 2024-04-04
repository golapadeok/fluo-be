package com.golapadeok.fluo.domain.workspace.dto.response;

import com.golapadeok.fluo.domain.workspace.dto.MemberWithRoleDto;
import lombok.Getter;

import java.util.List;

@Getter
public class WorkspaceSearchWithMembersResponse {
    private final String title;
    private final List<MemberWithRoleDto> members;

    public WorkspaceSearchWithMembersResponse(String title, List<MemberWithRoleDto> members) {
        this.title = title;
        this.members = members;
    }
}
