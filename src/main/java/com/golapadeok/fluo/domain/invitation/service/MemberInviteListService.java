package com.golapadeok.fluo.domain.invitation.service;

import com.golapadeok.fluo.common.security.domain.PrincipalDetails;
import com.golapadeok.fluo.domain.invitation.dto.response.InvitationWorkspaceInfo;
import com.golapadeok.fluo.domain.invitation.dto.response.MemberInvitationListResponse;
import com.golapadeok.fluo.domain.invitation.repository.InvitationQueryRepository;
import com.golapadeok.fluo.domain.invitation.repository.InvitationRepository;
import com.golapadeok.fluo.domain.invitation.domain.Invitation;
import com.golapadeok.fluo.domain.member.domain.Member;
import com.golapadeok.fluo.domain.invitation.dto.request.CursorPageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberInviteListService {

    private Long cursorId;
    private final InvitationQueryRepository invitationQueryRepository;

    @Transactional(readOnly = true)
    public MemberInvitationListResponse getInvitationList(PrincipalDetails principalDetails, CursorPageRequest cursorPageRequest) {
        log.info("getInvitationList({}, {}) invoked.", principalDetails.getMember(), cursorPageRequest.getCursorId());

        Member member = principalDetails.getMember();

        // 페이징 처리를 한 초대 목록
        Page<Invitation> invitations = this.invitationQueryRepository.findMemberWithInvitationList(member.getId(), cursorPageRequest);
        this.setCursorId(invitations);

        // 초대 목록에서 워크스페이스 정보 뽑기
        List<InvitationWorkspaceInfo> items = invitations.stream()
                .map(InvitationWorkspaceInfo::of)
                .toList();

        return MemberInvitationListResponse.builder()
                .total(String.valueOf(invitations.getTotalElements()))
                .cursorId(String.valueOf(cursorId))
                .items(items)
                .build();
    }

    private void setCursorId(Page<Invitation> page) {
        if(!page.hasNext()) {
            this.cursorId = null;
            return;
        }
        this.cursorId = page.getContent().get(page.toList().size()-1).getId();
    }

}
