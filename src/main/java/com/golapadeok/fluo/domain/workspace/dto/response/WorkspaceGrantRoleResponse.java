package com.golapadeok.fluo.domain.workspace.dto.response;

import com.golapadeok.fluo.domain.member.domain.Member;
import com.golapadeok.fluo.domain.role.domain.Role;
import com.golapadeok.fluo.domain.workspace.dto.RoleDto;
import com.golapadeok.fluo.domain.workspace.dto.request.WorkspaceGrantRoleRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class WorkspaceGrantRoleResponse {

    private String memberId;
    private String email;
    private String name;
    private String profileUrl;
    private RoleInfoResponse role;

    public WorkspaceGrantRoleResponse(Member member, Role role) {
        this.memberId = String.valueOf(member.getId());
        this.email = member.getEmail();
        this.name = member.getName();
        this.profileUrl = member.getProfile();
        this.role = new RoleInfoResponse(String.valueOf(role.getId()), role.getName());
    }

    @Getter
    @AllArgsConstructor
    public static class RoleInfoResponse {
        private String roleId;
        private String name;
    }
}
