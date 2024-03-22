package com.golapadeok.fluo.domain.role.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@AllArgsConstructor
@Getter
public class UpdateRoleResponse {

    private String workspaceId;
    private WorkspaceRoleListResponse items;

}
