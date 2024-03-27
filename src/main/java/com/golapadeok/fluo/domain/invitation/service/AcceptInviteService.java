package com.golapadeok.fluo.domain.invitation.service;

import com.golapadeok.fluo.common.security.domain.PrincipalDetails;
import com.golapadeok.fluo.domain.invitation.domain.Invitation;
import com.golapadeok.fluo.domain.invitation.dto.response.InvitationAnswerResponse;
import com.golapadeok.fluo.domain.invitation.exception.InvitationErrorStatus;
import com.golapadeok.fluo.domain.invitation.exception.InvitationException;
import com.golapadeok.fluo.domain.invitation.repository.InvitationRepository;
import com.golapadeok.fluo.domain.member.domain.WorkspaceMember;
import com.golapadeok.fluo.domain.member.repository.WorkspaceMemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AcceptInviteService {

    private final InvitationRepository invitationRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;

    public InvitationAnswerResponse acceptInvitation(PrincipalDetails principalDetails, String invitationsId) {
        Invitation invitation = this.invitationRepository.findById(Long.valueOf(invitationsId))
                .orElseThrow(() -> new InvitationException(InvitationErrorStatus.NOT_FOUND_INVITATION));

        WorkspaceMember workspaceMember = WorkspaceMember.builder()
                .member(principalDetails.getMember())
                .workspace(invitation.getWorkspace())
                .build();
        Long saved = this.workspaceMemberRepository.save(workspaceMember).getId();

        String message;
        if(saved != null) {
            message = "가입을 성공했습니다.";
            this.invitationRepository.delete(invitation); // 가입이 성공하면 초대 목록에서 제거
        }else{
            message = "가입을 실패했습니다.";
        }

        return new InvitationAnswerResponse(message);
    }

}
