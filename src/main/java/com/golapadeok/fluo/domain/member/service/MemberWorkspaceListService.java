package com.golapadeok.fluo.domain.member.service;

import com.golapadeok.fluo.common.security.domain.PrincipalDetails;
import com.golapadeok.fluo.domain.member.domain.Member;
import com.golapadeok.fluo.domain.member.domain.WorkspaceMember;
import com.golapadeok.fluo.domain.member.dto.response.MemberWorkspaceListResponse;
import com.golapadeok.fluo.domain.member.dto.response.WorkspaceInfoResponse;
import com.golapadeok.fluo.domain.member.dto.response.WorkspaceWithMemberInfoResponse;
import com.golapadeok.fluo.domain.member.repository.MemberRepository;
import com.golapadeok.fluo.domain.member.repository.WorkspaceMemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberWorkspaceListService {

    private static final int PAGE_DEFAULT_SIZE = 5;
    private Long lastWorkspaceId;
    private final MemberRepository memberRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;
    @Transactional
    public MemberWorkspaceListResponse getWorkspaceList(PrincipalDetails principalDetails, Long cursorId) {
        Pageable pageable = PageRequest.of(0, PAGE_DEFAULT_SIZE);
        
        Member member = principalDetails.getMember();

        Member findMember = this.memberRepository.findById(member.getId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Page<WorkspaceMember> workspaceMembers = getWorkspaceMembersList(findMember, cursorId, pageable);

        List<WorkspaceInfoResponse> items = getWorkspaceInfo(workspaceMembers);
        items.forEach(i -> log.info("item : {}", i));
        return MemberWorkspaceListResponse.builder()
                .total(String.valueOf(workspaceMembers.getTotalElements()))
                .cursorId(String.valueOf(lastWorkspaceId))
                .items(items)
                .build();
    }

    // 커서 기반 페이징 처리
    private Page<WorkspaceMember> getWorkspaceMembersList(Member findMember, Long cursorId, Pageable pageable) {
        Page<WorkspaceMember> slice;
        if(cursorId == null) {
            slice = this.workspaceMemberRepository.findByMemberIdOrderByIdDesc(findMember.getId(), pageable);
            setLastWorkspaceId(slice);
        }else{
            slice = this.workspaceMemberRepository.findByIdLessThanAndMemberIdOrderByIdDesc(cursorId, findMember.getId(), pageable);
            setLastWorkspaceId(slice);
        }
        return slice;
    }

    private void setLastWorkspaceId(Page<WorkspaceMember> slice) {
        this.lastWorkspaceId = slice.getContent().get(slice.toList().size()-1).getId();
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
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 워크스페이스 입니다."));

        return WorkspaceWithMemberInfoResponse.of(workspaceMember);
    }

}
