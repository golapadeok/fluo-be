package com.golapadeok.fluo.domain.role.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Builder
@AllArgsConstructor
@Getter
public class UpdateRoleResponse {

    private String workspaceId;
    private WorkspaceRoleListResponse items;

}
