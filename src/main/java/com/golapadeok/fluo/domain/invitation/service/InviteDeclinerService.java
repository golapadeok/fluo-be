package com.golapadeok.fluo.domain.invitation.service;

import com.golapadeok.fluo.common.security.domain.PrincipalDetails;
import com.golapadeok.fluo.domain.invitation.domain.Invitation;
import com.golapadeok.fluo.domain.invitation.dto.response.InvitationAnswerResponse;
import com.golapadeok.fluo.domain.invitation.exception.InvitationErrorStatus;
import com.golapadeok.fluo.domain.invitation.exception.InvitationException;
import com.golapadeok.fluo.domain.invitation.repository.InvitationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class InviteDeclinerService {

    private final InvitationRepository invitationRepository;

    public InvitationAnswerResponse declinerInvitation(String invitationId) {
        Invitation invitation = this.invitationRepository.findById(Long.valueOf(invitationId))
                .orElseThrow(() -> new InvitationException(InvitationErrorStatus.NOT_FOUND_INVITATION));

        // 존재하는 초대였는지를 조회한 후 존재한다면 초대목록에서 삭제
        this.invitationRepository.delete(invitation);

        return new InvitationAnswerResponse("초대를 거절했습니다.");
    }

}
