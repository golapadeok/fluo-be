package com.golapadeok.fluo.domain.role.api;

import com.golapadeok.fluo.common.annotation.AuthCheck;
import com.golapadeok.fluo.domain.role.domain.Credential;
import com.golapadeok.fluo.domain.role.dto.request.RoleCreateRequest;
import com.golapadeok.fluo.domain.role.dto.request.RoleUpdateRequest;
import com.golapadeok.fluo.domain.role.dto.response.BaseResponse;
import com.golapadeok.fluo.domain.role.dto.response.CreateRoleResponse;
import com.golapadeok.fluo.domain.role.dto.response.RoleDeleteResponse;
import com.golapadeok.fluo.domain.role.dto.response.UpdateRoleResponse;
import com.golapadeok.fluo.domain.role.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@Tag(name = "역할 및 권한 API", description = "역할 및 권한 관련 API 목록입니다.")
public class RoleController {

    private final RoleService roleService;

    @Operation(summary = "권한 전체 목록 조회", description = "권한의 전체 목록을 가져온다.")
    @GetMapping("/credential")
    public ResponseEntity<BaseResponse> getCredential() {
        log.info("getCredential");
        return ResponseEntity.ok(this.roleService.getAllCredential());
    }

    @AuthCheck(credential = Credential.CREATE_ROLE)
    @Operation(summary = "워크스페이스 역할 추가", description = "워크스페이스ID에 해당하는 워크스페이스에 역할을 추가")
    @PostMapping("/workspaces/{workspaceId}/role")
    public ResponseEntity<CreateRoleResponse> includeWorkspaceRole(@PathVariable("workspaceId") Integer workspaceId,
                                                                   @Parameter(description = "역할의 이름과 권한 리스트")
                                               @Valid @RequestBody RoleCreateRequest request) {
        CreateRoleResponse response = this.roleService.createWorkspaceRole(workspaceId, request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @Operation(summary = "워크스페이스의 역할 목록 조회", description = "워크스페이스ID에 해당하는 역할 목록을 조회")
    @GetMapping("/workspaces/{workspaceId}/role")
    public ResponseEntity<BaseResponse> getWorkspaceRoleList(@PathVariable("workspaceId") Integer workspaceId) {
        return ResponseEntity.ok(this.roleService.getWorkspaceRoleList(workspaceId));
    }

    @AuthCheck(credential = Credential.DELETE_ROLE)
    @Operation(summary = "역할 삭제", description = "역할ID를 통해 역할을 삭제")
    @DeleteMapping("/role/{roleId}")
    public ResponseEntity<RoleDeleteResponse> deleteWorkspaceRole(@PathVariable("roleId") Integer roleId) {
        return ResponseEntity.ok(this.roleService.deleteWorkspaceRole(roleId));
    }

    @AuthCheck(credential = Credential.MODIFY_ROLE)
    @Operation(summary = "워크스페이스의 역할 정보 수정", description = "워크스페이스 ID와 역할 ID를 통해 역할의 정보 수정")
    @PutMapping("/workspaces/{workspaceId}/roles/{roleId}")
    public ResponseEntity<UpdateRoleResponse> updateWorkspaceRole(@PathVariable("workspaceId") Integer workspaceId,
                                                                  @PathVariable("roleId") Integer roleId,
                                                                  @Valid @RequestBody RoleUpdateRequest request) {
        return ResponseEntity.ok(this.roleService.updateWorkspaceRole(workspaceId, roleId ,request));
    }


}
