package com.golapadeok.fluo.domain.member.api;

import com.golapadeok.fluo.common.security.domain.PrincipalDetails;
import com.golapadeok.fluo.domain.member.domain.Member;
import com.golapadeok.fluo.domain.member.dto.request.CursorPageRequest;
import com.golapadeok.fluo.domain.member.dto.response.MemberInfoResponse;
import com.golapadeok.fluo.domain.member.dto.response.MemberWorkspaceListResponse;
import com.golapadeok.fluo.domain.member.service.MemberWorkspaceListService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@Tag(name = "멤버 API 목록", description = "멤버 API 목록 입니다.")
public class MemberController {

    private final MemberWorkspaceListService memberWorkspaceListService;

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
    public ResponseEntity<MemberWorkspaceListResponse> getMyWorkspace(@AuthenticationPrincipal PrincipalDetails principalDetails,
                                                                      @Valid @ParameterObject CursorPageRequest cursorPageRequest) {

        return ResponseEntity.ok(this.memberWorkspaceListService.getWorkspaceList(principalDetails, cursorPageRequest));
    }

}
