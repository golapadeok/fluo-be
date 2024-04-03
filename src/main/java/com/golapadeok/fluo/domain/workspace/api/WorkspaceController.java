package com.golapadeok.fluo.domain.workspace.api;

import com.golapadeok.fluo.common.security.domain.PrincipalDetails;
import com.golapadeok.fluo.common.util.CookieUtils;
import com.golapadeok.fluo.domain.invitation.dto.InvitationCodeDto;
import com.golapadeok.fluo.domain.workspace.dto.request.*;
import com.golapadeok.fluo.domain.workspace.dto.response.*;
import com.golapadeok.fluo.domain.workspace.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private final WorkspaceUpdateService workspaceUpdateService;
    private final WorkspaceGrantRoleService workspaceGrantRoleService;

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
    public ResponseEntity<WorkspaceSearchWithMembersResponse> searchWorkspaceWithMembers(
            @PathVariable("workspaceId") Integer workspaceId
    ) {
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

    @GetMapping("/{workspaceId}/tags")
    @Operation(summary = "워크스페이스에 포함된 태그목록 조회 API", description = "해당 워크스페이스에 포함된 태그목록을 조회합니다.")
    public ResponseEntity<WorkspaceSearchWithTagsResponse> searchWorkspaceWithTags(
            @PathVariable(name = "workspaceId") Integer workspaceId
    ) {
        return ResponseEntity.ok(workspaceSearchService.searchWithTags(workspaceId));
    }

    @GetMapping("/{workspaceId}/invitation")
    @Operation(summary = "워크스페이스의 초대코드 조회 API", description = "해당 워크스페이스에 초대코드를 조회합니다.")
    public ResponseEntity<InvitationCodeDto> getWorkspaceWithInvitations(
            @PathVariable(name = "workspaceId") Integer workspaceId
    ) {
        InvitationCodeDto invitationCodeDto = workspaceSearchService.searchWithInvitationCode(workspaceId);
        return ResponseEntity.ok(invitationCodeDto);
    }

    //TODO KDY 임시 워크스페이스 생성 API에 인터셉터 workspaceId 쿠키 추가.
    @PostMapping
    @Operation(summary = "워크스페이스 생성 API", description = "새로운 워크스페이스를 생성합니다.")
    public ResponseEntity<WorkspaceResponse> createWorkspace(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @Valid @RequestBody WorkspaceCreateRequest request,
            HttpServletResponse response
    ) {
        WorkspaceResponse result = workspaceCreateService.create(principalDetails, request);
        ResponseCookie cookie = CookieUtils.createCookie("workspaceId", result.getWorkspaceId(), response);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(result);
    }

    @PutMapping("/{workspaceId}")
    @Operation(summary = "워크스페이스 수정 API", description = "워크스페이스 이름을 수정합니다.")
    public ResponseEntity<WorkspaceResponse> updateWorkspace(
            @PathVariable(name = "workspaceId") Integer workspaceId,
            @Valid @RequestBody WorkspaceUpdateRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(workspaceUpdateService.update(workspaceId, request));
    }

    @DeleteMapping("/{workspaceId}")
    @Operation(summary = "워크스페이스 삭제 API", description = "해당 워크스페이스를 삭제합니다.")
    public ResponseEntity<WorkspaceDeleteResponse> deleteWorkspace(
            @PathVariable("workspaceId") Integer workspaceId
    ) {
        return ResponseEntity.ok(workspaceDeleteService.delete(workspaceId));
    }

    @DeleteMapping("/{workspaceId}/members")
    @Operation(summary = "워크스페이스의 해당 멤버 삭제 API", description = "해당 멤버를 삭제합니다.")
    public ResponseEntity<WorkspaceDeleteResponse> deleteMember(
            @PathVariable("workspaceId") Integer workspaceId,
            @Valid @RequestBody MemberDeleteRequest request
    ) {
        return ResponseEntity.ok(workspaceDeleteService.deleteMember(workspaceId, request.getMemberId()));
    }

    @PostMapping("/members/roles")
    @Operation(summary = "워크스페이스 멤버 역할 부여 API", description = "워크스페이스에서 멤버에게 역할을 부여합니다.")
    public ResponseEntity<WorkspaceGrantRoleResponse> grantWorkspaceRole(
            @Valid @RequestBody WorkspaceGrantRoleRequest request
    ) {
        return ResponseEntity.ok(this.workspaceGrantRoleService.grantRole(request));
    }


}
