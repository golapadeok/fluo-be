package com.golapadeok.fluo.domain.member.service;

import com.golapadeok.fluo.common.security.domain.PrincipalDetails;
import com.golapadeok.fluo.domain.member.domain.Invitation;
import com.golapadeok.fluo.domain.member.domain.Member;
import com.golapadeok.fluo.domain.member.dto.request.CursorPageRequest;
import com.golapadeok.fluo.domain.member.dto.response.InvitationWorkspaceInfo;
import com.golapadeok.fluo.domain.member.dto.response.MemberInvitationListResponse;
import com.golapadeok.fluo.domain.member.dto.response.WorkspaceInfo;
import com.golapadeok.fluo.domain.member.repository.InvitationRepository;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
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
public class MemberInvitationListService {

    private Long cursorId;
    private final InvitationRepository invitationRepository;

    @Transactional(readOnly = true)
    public MemberInvitationListResponse getInvitationList(PrincipalDetails principalDetails, CursorPageRequest cursorPageRequest) {
        log.info("getInvitationList({}, {}) invoked.", principalDetails.getMember(), cursorPageRequest.getCursorId());
        Pageable pageable = PageRequest.of(0, cursorPageRequest.getLimit());

        Member member = principalDetails.getMember();

        // 페이징 처리를 한 초대 목록
        Page<Invitation> invitations =
                getInvitationList(member, cursorPageRequest.getCursorId(), pageable);

        Long total = this.invitationRepository.countByMemberId(member.getId());

        // 초대 목록에서 워크스페이스 정보 뽑기
        List<InvitationWorkspaceInfo> items = invitations.stream()
                .map(InvitationWorkspaceInfo::of)
                .toList();

        return MemberInvitationListResponse.builder()
                .total(total.toString())
                .cursorId(String.valueOf(cursorId))
                .items(items)
                .build();
    }

    private Page<Invitation> getInvitationList(Member member, Integer cursorId,  Pageable pageable) {
        Page<Invitation> page;
        if(cursorId == 0) {
            page =  this.invitationRepository.findByMemberIdOrderByCreateDateDesc(member.getId(), pageable);
            this.setCursorId(page);
        }else {
            page = this.invitationRepository.findByIdLessThanAndMemberIdOrderByCreateDateDesc(Long.valueOf(cursorId), member.getId(), pageable);
            this.setCursorId(page);
        }
        return page;
    }

    private void setCursorId(Page<Invitation> page) {
        if(!page.hasNext()) {
            this.cursorId = null;
            return;
        }
        this.cursorId = page.getContent().get(page.toList().size()-1).getId();
    }

}
