package com.golapadeok.fluo.domain.workspace.dto;

import com.golapadeok.fluo.domain.role.domain.Role;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public class RoleDto {
    private final String roleId;
    private final String name;
    private final List<String> credentials;

    public RoleDto(Role role) {
        this.roleId = role.getId().toString();
        this.name = role.getName();
        this.credentials = Arrays.stream(role.getRoles().split(","))
                .toList();
    }
}
