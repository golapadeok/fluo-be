package com.golapadeok.fluo.domain.invitation.api;

import com.golapadeok.fluo.common.annotation.AuthCheck;
import com.golapadeok.fluo.domain.invitation.dto.request.InviteEmailRequest;
import com.golapadeok.fluo.domain.invitation.dto.response.InvitationEmailResponse;
import com.golapadeok.fluo.domain.invitation.service.WorkspaceInviteMemberEmailService;
import com.golapadeok.fluo.domain.role.domain.Credential;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@Tag(name = "워크스페이스 초대 관련 API 목록", description = "워크스페이스 초대 관련 API 목록 입니다.")
public class WorkspaceInvitationController {

    private final WorkspaceInviteMemberEmailService workspaceInviteMemberEmailService;

    @AuthCheck(credential = Credential.INVITE_MEMBER)
    @Operation(summary = "워크스페이스 멤버 이메일로 초대", description = "멤버 이메일을 통해 워크스페이스로 초대")
    @PostMapping("/workspaces/{workspaceId}/invitations")
    public ResponseEntity<InvitationEmailResponse> inviteWorkspaceWithMemberEmail(
            @PathVariable("workspaceId") String workspaceId,
            @Valid @RequestBody InviteEmailRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.workspaceInviteMemberEmailService.InviteMemberEmail(workspaceId,request));
    }

}
