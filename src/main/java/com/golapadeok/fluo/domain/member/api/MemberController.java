package com.golapadeok.fluo.domain.member.api;

import com.golapadeok.fluo.common.security.domain.PrincipalDetails;
import com.golapadeok.fluo.domain.member.domain.Member;
import com.golapadeok.fluo.domain.member.dto.response.MemberInfoResponse;
import com.golapadeok.fluo.domain.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@Tag(name = "멤버 API 목록", description = "멤버 API 목록 입니다.")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "내 정보 조회", description = "내 정보 조회를 합니다.")
    @GetMapping("/members/self")
    public ResponseEntity<MemberInfoResponse> getMyInfo(@AuthenticationPrincipal PrincipalDetails principalDetails) {
        Member member = principalDetails.getMember();

        MemberInfoResponse response = MemberInfoResponse.builder()
                .memberId(String.valueOf(member.getId()))
                .name(member.getName())
                .email(member.getEmail())
                .profileUrl(member.getProfile())
                .build();

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "멤버가 소속된 워크스페이스 조회", description = "멤버가 소속된 워크스페이스를 조회합니다.")
    @GetMapping("/members/self/workspaces")
    public void getMyWorkspace() {

    }

    @Operation(summary = "멤버가 받은 초대 목록", description = "멤버가 초대받은 워크스페이스의 초대 목록을 보여줍니다.")
    @GetMapping("/invitations/self")
    public void getMyInvitations() {

    }

    @Operation(summary = "초대코드로 워크스페이스 조회", description = "초대 코드 입력시 워크스페이스의 정보가 조회됩니다.")
    @GetMapping("/members/invitations/{invitationsCode}")
    public void getInvitationsWorkspaceInfo() {

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
