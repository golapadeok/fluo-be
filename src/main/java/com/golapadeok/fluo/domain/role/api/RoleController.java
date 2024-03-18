package com.golapadeok.fluo.domain.role.api;

import com.golapadeok.fluo.domain.role.dto.response.BaseResponse;
import com.golapadeok.fluo.domain.role.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        return ResponseEntity.ok(this.roleService.getAllCredential());
    }

    @Operation(summary = "워크스페이스 역할 추가", description = "워크스페이스ID에 해당하는 워크스페이스에 역할을 추가", hidden = true)
    @PostMapping("/workspaces/{workspaceId}/role")
    public ResponseEntity includeWorkspaceRole(@PathVariable("workspaceId") Integer workspaceId) {
        return null;
    }

    @Operation(summary = "워크스페이스의 역할 목록 조회", description = "워크스페이스ID에 해당하는 역할 목록을 조회", hidden = true)
    @GetMapping("/workspaces/{workspaceId}/role")
    public ResponseEntity<BaseResponse> getWorkspaceRoleList(@PathVariable("workspaceId") Integer workspaceId) {
        return ResponseEntity.ok(this.roleService.getWorkspaceRoleList(workspaceId));
    }

    @Operation(summary = "역할 삭제", description = "역할ID를 통해 역할을 삭제", hidden = true)
    @DeleteMapping("/role/{roleId}")
    public ResponseEntity deleteWorkspaceRole(@PathVariable("roleId") Integer roleId) {
        return null;
    }

    @Operation(summary = "워크스페이스의 역할 정보 수정", description = "워크스페이스 ID와 역할 ID를 통해 역할의 정보 수정", hidden = true)
    @PutMapping("/workspaces/{workspaceId}/roles/{roleId}")
    public ResponseEntity updateWorkspaceRole(@PathVariable("workspaceId") Integer workspaceId,
                                              @PathVariable("roleId") Integer roleId) {
        return null;
    }




}
