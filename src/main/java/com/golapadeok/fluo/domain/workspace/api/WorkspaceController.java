package com.golapadeok.fluo.domain.workspace.api;

import com.golapadeok.fluo.domain.workspace.dto.request.FilterRequest;
import com.golapadeok.fluo.domain.workspace.dto.request.WorkspaceCreateRequest;
import com.golapadeok.fluo.domain.workspace.dto.request.CursorPageRequest;
import com.golapadeok.fluo.domain.workspace.dto.response.*;
import com.golapadeok.fluo.domain.workspace.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/workspaces")
@Tag(name = "워크스페이스 API", description = "워크스페이스 관련 API 목록입니다.")
public class WorkspaceController {
    private final WorkspaceCreateService workspaceCreateService;
    private final WorkspaceSearchService workspaceSearchService;
    private final WorkspaceDeleteService workspaceDeleteService;


    @GetMapping
    @Operation(summary = "워크스페이스 전체조회 API", description = "워크스페이스 전체조회 API")
    public ResponseEntity<BaseResponse> getWorkspaces() {
        BaseResponse searches = workspaceSearchService.searches();
        return ResponseEntity.ok(searches);
    }

    @GetMapping("/{workspaceId}")
    @Operation(summary = "워크스페이스 단일조회 API", description = "해당 워크스페이스 조회 API")
    public ResponseEntity<WorkspaceSearchResponse> searchWorkspace(
            @PathVariable("workspaceId") Integer workspaceId
    ) {
        return ResponseEntity.ok(workspaceSearchService.search(workspaceId));
    }

    @GetMapping("/{workspaceId}/states")
    @Operation(summary = "워크스페이스 상태목록 조회 API", description = "해당 워크스페이스의 상태 목록을 조회합니다.")
    public ResponseEntity<WorkspaceSearchWithStatesResponse> getWorkspaceWithStates(
            @PathVariable("workspaceId") Integer workspaceId
    ) {
        return ResponseEntity.ok(workspaceSearchService.searchWithStates(workspaceId));
    }

    @GetMapping("/{workspaceId}/members")
    @Operation(summary = "워크스페이스의 회원목록 조회 API", description = "해당 워크스페이스의 회원목록을 조회합니다.")
    public ResponseEntity<WorkspaceSearchWithMembersResponse> searchWorkspaceWithMembers(@PathVariable("workspaceId") Integer workspaceId) {
        return ResponseEntity.ok(workspaceSearchService.searchWithMembers(workspaceId));
    }

    @GetMapping("/{workspaceId}/tasks")
    @Operation(summary = "워크스페이스에 포함된 업무목록 조회 API", description = "해당 워크스페이스와 포함된 업무목록을 조회합니다.")
    public ResponseEntity<WorkspaceSearchWithTasksResponse> searchWorkspaceWithTasks(
            @PathVariable(name = "workspaceId") Integer workspaceId,
            @Valid @ParameterObject CursorPageRequest pageRequest,
            @Valid @ParameterObject FilterRequest filterRequest
    ) {
        return ResponseEntity.ok(workspaceSearchService.searchWithTasks(workspaceId, pageRequest, filterRequest));
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
