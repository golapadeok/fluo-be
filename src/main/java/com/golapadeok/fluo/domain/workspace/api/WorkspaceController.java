package com.golapadeok.fluo.domain.workspace.api;

import com.golapadeok.fluo.domain.workspace.dto.*;
import com.golapadeok.fluo.domain.workspace.dto.request.StateRequest;
import com.golapadeok.fluo.domain.workspace.dto.request.WorkspaceRequest;
import com.golapadeok.fluo.domain.workspace.dto.response.BaseResponse;
import com.golapadeok.fluo.domain.workspace.service.WorkspaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/workspaces")
@Tag(name = "워크스페이스 API", description = "워크스페이스 관련 API 목록입니다.")
public class WorkspaceController {
    private final WorkspaceService workspaceService;

    @GetMapping
    @Operation(summary = "워크스페이스 전체조회 API", description = "워크스페이스 전체조회 API")
    public ResponseEntity<BaseResponse> getWorkspaces() {
        //전체조회
        //페이징
        //초대코드
        BaseResponse workspaces = workspaceService.getWorkspaces();
        return ResponseEntity.ok(workspaces);
    }

    @GetMapping("/{workspaceId}")
    @Operation(summary = "워크스페이스 단일조회 API", description = "워크스페이스 단일조회 API")
    public ResponseEntity<WorkspaceDto> getWorkspace(
            @PathVariable("workspaceId") Integer workspaceId
    ) {
        WorkspaceDto workspace = workspaceService.getWorkspace(workspaceId);
        return ResponseEntity.ok(workspace);
    }

    @GetMapping("/{workspaceId}/states")
    @Operation(summary = "워크스페이스 상태 전체조회 API", description = "해당 워크스페이스의 상태 목록을 조회합니다.")
    public ResponseEntity<WorkspaceWithStatesDto> getWorkspaceWithStates(
            @PathVariable("workspaceId") Integer workspaceId
    ) {
        WorkspaceWithStatesDto workspaceWithStates = workspaceService.getWorkspaceWithStates(workspaceId);
        return ResponseEntity.ok(workspaceWithStates);
    }

    @PostMapping("/{workspaceId}/states")
    @Operation(summary = "워크스페이스 상태 생성 API", description = "워크스페이스의 새로운 상태를 생성합니다.")
    public ResponseEntity<StateDto> createWorkspaceWithStates(
            @PathVariable("workspaceId") Integer workspaceId,
            @RequestBody StateRequest request
    ) {
        StateDto state = workspaceService.createState(workspaceId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(state);
    }

    @GetMapping("/{workspaceId}/members")
    @Operation(hidden = true)
    public void getWorkspaceWithMembers() {

    }

    @GetMapping("/{workspaceId}/tasks")
    @Operation(hidden = true)
    public void getWorkspaceWithTasks() {

    }

    @GetMapping("/{workspaceId}/invitations")
    @Operation(hidden = true)
    public void getWorkspaceWithInvitations() {

    }

    @PostMapping
    @Operation(summary = "워크스페이스 생성 API", description = "새로운 워크스페이스를 생성합니다.")
    public ResponseEntity<WorkspaceDto> createWorkspace(
            @Valid @RequestBody WorkspaceRequest request
    ) {
        WorkspaceDto workspace = workspaceService.createWorkspace(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(workspace);
    }

    @DeleteMapping("/{workspaceId}")
    @Operation(summary = "워크스페이스 삭제 API", description = "워크스페이스를 삭제합니다.")
    public ResponseEntity<WorkspaceIdDto> deleteWorkspace(
            @PathVariable("workspaceId") Integer workspaceId
    ) {
        WorkspaceIdDto workspaceIdDto = workspaceService.deleteWorkspace(workspaceId);
        return ResponseEntity.ok(workspaceIdDto);
    }
}
