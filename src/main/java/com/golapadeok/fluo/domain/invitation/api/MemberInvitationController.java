package com.golapadeok.fluo.domain.invitation.api;

import com.golapadeok.fluo.common.security.domain.PrincipalDetails;
import com.golapadeok.fluo.domain.invitation.dto.request.CursorPageRequest;
import com.golapadeok.fluo.domain.invitation.dto.response.MemberInvitationListResponse;
import com.golapadeok.fluo.domain.invitation.service.MemberInvitationListService;
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

    private final MemberInvitationListService memberInvitationListService;

    @Operation(summary = "멤버가 받은 초대 목록", description = "멤버가 초대받은 워크스페이스의 초대 목록을 보여줍니다.")
    @GetMapping("/invitations/self")
    public ResponseEntity<MemberInvitationListResponse> getMyInvitations(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                                         @Valid @ParameterObject CursorPageRequest cursorPageRequest) {
        return ResponseEntity.ok(this.memberInvitationListService.getInvitationList(principalDetails, cursorPageRequest));
    }

    @Operation(summary = "초대코드로 워크스페이스 조회", description = "초대 코드 입력시 워크스페이스의 정보가 조회됩니다.")
    @GetMapping("/members/invitations/{invitationsCode}")
    public void getInvitationsWorkspaceInfo(@PathVariable("invitationCode") String invitationCode) {

    }

    @Operation(summary = "초대 수락", description = "초대코드로 워크스페이스 조회 후 수락을 누르면 해당 워크스페이스에 멈베가 추가됩니다.")
    @PostMapping("/members/invitaions")
    public void includeWorkspaceMember() {

    }

    @Operation(summary = "초대 거절", description = "초대코드로 워크스페이스 조회 후 거절을 누르면 거절됩니다.")
    @DeleteMapping("members/invitations/{invitationsCode}")
    public void declineInvitation() {

    }
}
