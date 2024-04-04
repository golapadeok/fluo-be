package com.golapadeok.fluo.domain.workspace.dto.request;

import com.golapadeok.fluo.domain.workspace.dto.RoleDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Schema(name = "멤버 역할 부여 요청 데이터")
public class WorkspaceGrantRoleRequest {

    private Integer memberId;
    private RoleInfo role;


    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @AllArgsConstructor
    public static class RoleInfo {
        private String roleId;
        private String name;
    }
}
