package com.golapadeok.fluo.domain.member.service;

import com.golapadeok.fluo.common.security.domain.PrincipalDetails;
import com.golapadeok.fluo.domain.member.domain.Member;
import com.golapadeok.fluo.domain.member.domain.WorkspaceMember;
import com.golapadeok.fluo.domain.member.dto.request.CursorPageRequest;
import com.golapadeok.fluo.domain.member.dto.response.MemberWorkspaceListResponse;
import com.golapadeok.fluo.domain.member.dto.response.WorkspaceInfoResponse;
import com.golapadeok.fluo.domain.member.dto.response.WorkspaceWithMemberInfoResponse;
import com.golapadeok.fluo.domain.member.exception.MemberErrorStatus;
import com.golapadeok.fluo.domain.member.exception.MemberException;
import com.golapadeok.fluo.domain.member.repository.MemberRepository;
import com.golapadeok.fluo.domain.member.repository.WorkspaceMemberRepository;
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
public class MemberWorkspaceListService {

    private Long cursorId;
    private final MemberRepository memberRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;

    @Transactional(readOnly = true)
    public MemberWorkspaceListResponse getWorkspaceList(PrincipalDetails principalDetails, CursorPageRequest cursorPageRequest) {
        if (principalDetails == null || principalDetails.getMember() == null) {
            throw new MemberException(MemberErrorStatus.NOT_MEMBER_LOGIN);
        }

        Pageable pageable = PageRequest.of(0, cursorPageRequest.getLimit());
        
        Member member = principalDetails.getMember();

        Member findMember = this.memberRepository.findById(member.getId())
                .orElseThrow(() -> new MemberException(MemberErrorStatus.NOT_FOUND_MEMBER));

        Page<WorkspaceMember> workspaceMembers =
                getWorkspaceMembersList(findMember, cursorPageRequest.getCursorId(), pageable);

        Long total = this.workspaceMemberRepository.countByMemberId(member.getId());

        List<WorkspaceInfoResponse> items = getWorkspaceInfo(workspaceMembers);

        return MemberWorkspaceListResponse.builder()
                .total(String.valueOf(total))
                .cursorId(String.valueOf(this.cursorId))
                .items(items)
                .build();
    }

    // 커서 기반 페이징 처리
    private Page<WorkspaceMember> getWorkspaceMembersList(Member findMember, Integer cursorId, Pageable pageable) {
        Page<WorkspaceMember> page;
        if(cursorId == 0) {
            page = this.workspaceMemberRepository.findByMemberIdOrderByIdDesc(findMember.getId(), pageable);
            setLastWorkspaceId(page);
        }else{
            page = this.workspaceMemberRepository.findByIdLessThanAndMemberIdOrderByIdDesc(Long.valueOf(cursorId), findMember.getId(), pageable);
            setLastWorkspaceId(page);
        }
        return page;
    }

    private void setLastWorkspaceId(Page<WorkspaceMember> page) {
        if(!page.hasNext()) {
            this.cursorId = null;
            return;
        }
        this.cursorId = page.getContent().get(page.toList().size()-1).getId();
    }

    // 워크스페이스의 정보를 조회
    private List<WorkspaceInfoResponse> getWorkspaceInfo(Page<WorkspaceMember> workspaceMembers) {
        return workspaceMembers.stream()
                .map(WorkspaceMember::getWorkspace)
                .map(w -> WorkspaceInfoResponse.of(w, getWorkspaceWithMembers(w.getId())))
                .toList();
    }

    // 워크스페이스에 소속된 멤버들 조회
    private List<WorkspaceWithMemberInfoResponse> getWorkspaceWithMembers(Long workspaceId) {
        List<WorkspaceMember> workspaceMember = this.workspaceMemberRepository.findByWorkspaceId(workspaceId)
                .orElseThrow(() -> new MemberException(MemberErrorStatus.NOT_FOUND_WORKSPACE));

        return WorkspaceWithMemberInfoResponse.of(workspaceMember);
    }

}
