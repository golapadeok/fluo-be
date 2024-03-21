package com.golapadeok.fluo.domain.member.service;

import com.golapadeok.fluo.common.security.domain.PrincipalDetails;
import com.golapadeok.fluo.domain.member.domain.Member;
import com.golapadeok.fluo.domain.member.domain.WorkspaceMember;
import com.golapadeok.fluo.domain.member.dto.response.MemberWorkspaceListResponse;
import com.golapadeok.fluo.domain.member.dto.response.WorkspaceInfoResponse;
import com.golapadeok.fluo.domain.member.dto.response.WorkspaceWithMemberInfoResponse;
import com.golapadeok.fluo.domain.member.repository.MemberRepository;
import com.golapadeok.fluo.domain.member.repository.WorkspaceMemberRepository;
import com.golapadeok.fluo.domain.workspace.repository.WorkspaceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberWorkspaceListService {

    private final MemberRepository memberRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;
    @Transactional
    public MemberWorkspaceListResponse getWorkspaceList(PrincipalDetails principalDetails, String cursorId, String limit) {
        Member member = principalDetails.getMember();

        Member findMember = this.memberRepository.findById(member.getId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        List<WorkspaceMember> workspaceMembers = findMember.getWorkspaceMembers();

        List<WorkspaceInfoResponse> items = getWorkspaceInfo(workspaceMembers);

        return MemberWorkspaceListResponse.builder()
                .total(null)
                .cursorId(null)
                .items(items)
                .build();
    }

    private List<WorkspaceInfoResponse> getWorkspaceInfo(List<WorkspaceMember> workspaceMembers) {
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
