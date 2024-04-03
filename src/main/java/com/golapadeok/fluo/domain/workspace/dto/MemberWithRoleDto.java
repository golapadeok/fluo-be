package com.golapadeok.fluo.domain.workspace.dto;

import com.golapadeok.fluo.domain.member.domain.Member;
import com.golapadeok.fluo.domain.role.domain.Role;
import lombok.Getter;

import java.util.List;

@Getter
public class MemberWithRoleDto {
    private final String memberId;
    private final String name;
    private final String email;
    private final String profileUrl;
    private final RoleDto roles;

    public MemberWithRoleDto(Member member, Role roles) {
        this.memberId = member.getId().toString();
        this.name = member.getName();
        this.email = member.getEmail();
        this.profileUrl = member.getProfile();
        this.roles = new RoleDto(roles);
    }
}
