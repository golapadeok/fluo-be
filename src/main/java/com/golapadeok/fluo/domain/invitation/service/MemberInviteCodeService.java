package com.golapadeok.fluo.domain.invitation.service;

import com.golapadeok.fluo.common.security.domain.PrincipalDetails;
import com.golapadeok.fluo.domain.invitation.dto.response.InvitationAnswerResponse;
import com.golapadeok.fluo.domain.invitation.exception.InvitationErrorStatus;
import com.golapadeok.fluo.domain.invitation.exception.InvitationException;
import com.golapadeok.fluo.domain.member.domain.Member;
import com.golapadeok.fluo.domain.member.domain.WorkspaceMember;
import com.golapadeok.fluo.domain.member.repository.WorkspaceMemberRepository;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import com.golapadeok.fluo.domain.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberInviteCodeService {

    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;

    public InvitationAnswerResponse saveMemberInviteCode(PrincipalDetails principalDetails, String invitationsCode) {
        if (principalDetails == null || principalDetails.getMember() == null) {
            throw new IllegalArgumentException("로그인해야 합니다.");
        }

        Member member = principalDetails.getMember();

        Workspace workspace = this.workspaceRepository.findByInvitationCode(invitationsCode)
                .orElseThrow(() -> new InvitationException(InvitationErrorStatus.INVALID_INVITATION_CODE));

        WorkspaceMember workspaceMember = WorkspaceMember.builder()
                .member(member)
                .workspace(workspace)
                .build();

        this.workspaceMemberRepository.save(workspaceMember);
        return new InvitationAnswerResponse("가입을 성공했습니다.");
    }

}
