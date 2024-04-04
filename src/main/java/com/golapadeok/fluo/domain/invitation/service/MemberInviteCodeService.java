package com.golapadeok.fluo.domain.invitation.service;

import com.golapadeok.fluo.common.security.domain.PrincipalDetails;
import com.golapadeok.fluo.domain.invitation.dto.response.InvitationAnswerResponse;
import com.golapadeok.fluo.domain.invitation.exception.InvitationErrorStatus;
import com.golapadeok.fluo.domain.invitation.exception.InvitationException;
import com.golapadeok.fluo.domain.member.domain.Member;
import com.golapadeok.fluo.domain.member.domain.WorkspaceMember;
import com.golapadeok.fluo.domain.member.repository.WorkspaceMemberRepository;
import com.golapadeok.fluo.domain.role.domain.MemberRole;
import com.golapadeok.fluo.domain.role.domain.Role;
import com.golapadeok.fluo.domain.role.repository.MemberRoleRepository;
import com.golapadeok.fluo.domain.role.repository.RoleRepository;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import com.golapadeok.fluo.domain.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberInviteCodeService {

    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;
    private final RoleRepository roleRepository;
    private final MemberRoleRepository memberRoleRepository;

    @Transactional
    public InvitationAnswerResponse saveMemberInviteCode(PrincipalDetails principalDetails, String invitationsCode) {
        if (principalDetails == null || principalDetails.getMember() == null) {
            throw new IllegalArgumentException("로그인해야 합니다.");
        }

        Member member = principalDetails.getMember();

        Workspace workspace = this.workspaceRepository.findByInvitationCode(invitationsCode)
                .orElseThrow(() -> new InvitationException(InvitationErrorStatus.INVALID_INVITATION_CODE));

        // 이미 있는 회원인지 검증 로직
        this.workspaceMemberRepository.findByMemberIdAndWorkspaceId(member.getId(), workspace.getId())
                .ifPresent(wm -> {
                    throw new InvitationException(InvitationErrorStatus.ALREADY_JOIN_WORKSPACE);
                });
        
        WorkspaceMember workspaceMember = WorkspaceMember.builder()
                .member(member)
                .workspace(workspace)
                .build();

        Role generateRole = this.roleRepository.findByNameAndWorkspaceId(workspace.getId())
                .orElseThrow(() -> new InvitationException(InvitationErrorStatus.NOT_FOUND_ROLE));

        // 역할 부여
        giveGenerateRole(member, generateRole);

        this.workspaceMemberRepository.save(workspaceMember);
        return new InvitationAnswerResponse("가입을 성공했습니다.");
    }

    private void giveGenerateRole(Member member, Role generateRole) {
        MemberRole memberRole = MemberRole.builder()
                .member(member)
                .role(generateRole)
                .build();
        this.memberRoleRepository.save(memberRole);
    }

}
