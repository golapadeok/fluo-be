package com.golapadeok.fluo.domain.workspace.dto.response;

import com.golapadeok.fluo.domain.workspace.dto.MemberWithRoleDto;
import lombok.Getter;

import java.util.List;

@Getter
public class WorkspaceSearchWithMembersResponse {
    private final List<MemberWithRoleDto> members;

    public WorkspaceSearchWithMembersResponse(List<MemberWithRoleDto> members) {
        this.members = members;
    }
}
