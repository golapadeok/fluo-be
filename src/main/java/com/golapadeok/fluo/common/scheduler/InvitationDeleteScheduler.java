package com.golapadeok.fluo.common.scheduler;

import com.golapadeok.fluo.domain.invitation.domain.Invitation;
import com.golapadeok.fluo.domain.invitation.repository.InvitationRepository;
import com.golapadeok.fluo.domain.member.domain.Member;
import com.golapadeok.fluo.domain.member.repository.MemberRepository;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import com.golapadeok.fluo.domain.workspace.repository.WorkspaceRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Component
public class InvitationDeleteScheduler {

    private final InvitationRepository invitationRepository;

    @Scheduled(cron = "0 0 0 * * *") // 자정마다 스케줄러가 돌도록 설정
    public void deleteInvitationByWorkspace() {

        // 현재 날짜 가져오기
        LocalDateTime now = LocalDateTime.now();

        // invitation 전체 가져오기
        List<Invitation> invitations = this.invitationRepository.findAll();

        /**
         * invitation에서 true 또는 false 인 경우
         * 즉, isPending이 null이 아닐 경우 수락 또는 거절을 한것이기 때문에
         * invitation에서 제거하는 로직
         */
        invitations.forEach(invitation -> {
            LocalDateTime createDate = invitation.getCreateDate();
            Boolean isPending = invitation.getIsPending();

            if(createDate != null && createDate.isBefore(now) && isPending != null) {
                this.invitationRepository.delete(invitation);
            }
        });

    }

}
