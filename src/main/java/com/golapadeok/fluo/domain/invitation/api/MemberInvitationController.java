package com.golapadeok.fluo.domain.invitation.api;

import com.golapadeok.fluo.common.security.domain.PrincipalDetails;
import com.golapadeok.fluo.domain.invitation.dto.request.CursorPageRequest;
import com.golapadeok.fluo.domain.invitation.dto.response.InvitationAnswerResponse;
import com.golapadeok.fluo.domain.invitation.dto.response.InvitationWithWorkspaceInfoResponse;
import com.golapadeok.fluo.domain.invitation.dto.response.MemberInvitationListResponse;
import com.golapadeok.fluo.domain.invitation.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@Tag(name = "멤버 초대 관련 API 목록", description = "멤버 초대 관련 API 목록 입니다.")
public class MemberInvitationController {

    private final MemberInviteListService memberInvitationListService;
    private final MemberInviteWorkspaceService memberInvitationWorkspaceService;
    private final AcceptInviteService acceptInvitationService;
    private final InviteDeclinerService inviteDeclinerService;
    private final MemberInviteCodeService memberInviteCodeService;

    @Operation(summary = "멤버가 받은 초대 목록", description = "멤버가 초대받은 워크스페이스의 초대 목록을 보여줍니다.")
    @GetMapping("/invitations/self")
    public ResponseEntity<MemberInvitationListResponse> getMyInvitations(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                                         @Valid @ParameterObject CursorPageRequest cursorPageRequest) {
        return ResponseEntity.ok(this.memberInvitationListService.getInvitationList(principalDetails, cursorPageRequest));
    }

    @Valid
    @Operation(summary = "초대코드로 워크스페이스 조회", description = "초대 코드 입력시 워크스페이스의 정보가 조회됩니다.")
    @GetMapping("/members/invitations/{invitationsCode}")
    public ResponseEntity<InvitationWithWorkspaceInfoResponse> getInvitationsWorkspaceInfo(
            @PathVariable("invitationsCode")
            String invitationsCode) {
        return ResponseEntity.ok(this.memberInvitationWorkspaceService.searchWorkspaceByInvitationCode(invitationsCode));
    }

    @Operation(summary = "초대코드로 워크스페이스 가입", description = "워크스페이스 정보를 조회 후 가입을 희망하면 워크스페이스에 가입")
    @PostMapping("/members/invitations/{invitationsCode}")
    public ResponseEntity<InvitationAnswerResponse> inviteWorkspaceWithMemberInviteCode(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable("invitationsCode") String invitationsCode) {
        return ResponseEntity.ok(this.memberInviteCodeService.saveMemberInviteCode(principalDetails, invitationsCode));
    }

    @Operation(summary = "초대 수락", description = "초대코드로 워크스페이스 조회 후 수락을 누르면 해당 워크스페이스에 멤버가 추가되며 초대목록이 삭제됩니다.")
    @PostMapping("/members/invitations/{invitationsId}")
    public ResponseEntity<InvitationAnswerResponse> includeWorkspaceMember(
            @AuthenticationPrincipal PrincipalDetails principalDetails,
            @PathVariable("invitationsId") String invitationsId) {
        return ResponseEntity.ok(this.acceptInvitationService.acceptInvitation(principalDetails, invitationsId));
    }

    @Operation(summary = "초대 거절", description = "초대코드로 워크스페이스 조회 후 거절을 누르면 초대목록이 삭제되며 거절됩니다.")
    @DeleteMapping("members/invitations/{invitationsId}")
    public ResponseEntity<InvitationAnswerResponse> declineInvitation(
            @PathVariable("invitationsId") String invitationsId) {
        return ResponseEntity.ok(this.inviteDeclinerService.declinerInvitation(invitationsId));
    }
}
