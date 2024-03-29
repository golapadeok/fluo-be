package com.golapadeok.fluo.domain.workspace.dto.response;

import com.golapadeok.fluo.domain.member.domain.Member;
import com.golapadeok.fluo.domain.role.domain.Role;
import com.golapadeok.fluo.domain.workspace.dto.RoleDto;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class WorkspaceGrantRoleResponse {

    private String memberId;
    private String email;
    private String name;
    private String profileUrl;
    private RoleDto role;

    public WorkspaceGrantRoleResponse(Member member, Role role) {
        this.memberId = String.valueOf(member.getId());
        this.email = member.getEmail();
        this.name = member.getName();
        this.profileUrl = member.getProfile();
        this.role = new RoleDto(role);
    }
}
