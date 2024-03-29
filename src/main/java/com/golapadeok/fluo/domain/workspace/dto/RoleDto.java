package com.golapadeok.fluo.domain.workspace.dto;

import com.golapadeok.fluo.domain.role.domain.Role;
import lombok.Getter;

@Getter
public class RoleDto {
    private final String roleId;
    private final String name;

    public RoleDto(Role role) {
        this.roleId = role.getId().toString();
        this.name = role.getName();
    }
}
