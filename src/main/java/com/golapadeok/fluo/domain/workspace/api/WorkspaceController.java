package com.golapadeok.fluo.domain.workspace.api;

import com.golapadeok.fluo.domain.state.StateDto;
import com.golapadeok.fluo.domain.workspace.dto.*;
import com.golapadeok.fluo.domain.workspace.dto.request.StateRequest;
import com.golapadeok.fluo.domain.workspace.dto.request.WorkspaceCreateRequest;
import com.golapadeok.fluo.domain.workspace.dto.response.BaseResponse;
import com.golapadeok.fluo.domain.workspace.dto.response.WorkspaceCreateResponse;
import com.golapadeok.fluo.domain.workspace.dto.response.WorkspaceDeleteResponse;
import com.golapadeok.fluo.domain.workspace.dto.response.WorkspaceSearchResponse;
import com.golapadeok.fluo.domain.workspace.service.WorkspaceCreateService;
import com.golapadeok.fluo.domain.workspace.service.WorkspaceDeleteService;
import com.golapadeok.fluo.domain.workspace.service.WorkspaceSearchService;
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

    private final WorkspaceCreateService workspaceCreateService;
    private final WorkspaceSearchService workspaceSearchService;
    private final WorkspaceDeleteService workspaceDeleteService;


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
    @Operation(summary = "워크스페이스 단일조회 API", description = "해당 워크스페이스 조회 API")
    public ResponseEntity<WorkspaceSearchResponse> searchWorkspace(
            @PathVariable("workspaceId") Integer workspaceId
    ) {
        return ResponseEntity.ok(workspaceSearchService.search(workspaceId));
    }

    @GetMapping("/{workspaceId}/states")
    @Operation(summary = "워크스페이스 상태 전체조회 API", description = "해당 워크스페이스의 상태 목록을 조회합니다.")
    public ResponseEntity<WorkspaceWithStatesDto> getWorkspaceWithStates(
            @PathVariable("workspaceId") Integer workspaceId
    ) {
        WorkspaceWithStatesDto workspaceWithStates = workspaceService.getWorkspaceWithStates(workspaceId);
        return ResponseEntity.ok(workspaceWithStates);
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
    public ResponseEntity<WorkspaceCreateResponse> createWorkspace(
            @Valid @RequestBody WorkspaceCreateRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(workspaceCreateService.create(request));
    }

    @DeleteMapping("/{workspaceId}")
    @Operation(summary = "워크스페이스 삭제 API", description = "해당 워크스페이스를 삭제합니다.")
    public ResponseEntity<WorkspaceDeleteResponse> deleteWorkspace(
            @PathVariable("workspaceId") Integer workspaceId
    ) {
        return ResponseEntity.ok(workspaceDeleteService.delete(workspaceId));
    }
}
