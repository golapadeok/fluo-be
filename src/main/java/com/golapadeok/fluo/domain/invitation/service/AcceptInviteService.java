package com.golapadeok.fluo.domain.invitation.service;

import com.golapadeok.fluo.common.security.domain.PrincipalDetails;
import com.golapadeok.fluo.domain.invitation.domain.Invitation;
import com.golapadeok.fluo.domain.invitation.dto.response.InvitationAnswerResponse;
import com.golapadeok.fluo.domain.invitation.exception.InvitationErrorStatus;
import com.golapadeok.fluo.domain.invitation.exception.InvitationException;
import com.golapadeok.fluo.domain.invitation.repository.InvitationRepository;
import com.golapadeok.fluo.domain.member.domain.Member;
import com.golapadeok.fluo.domain.member.domain.WorkspaceMember;
import com.golapadeok.fluo.domain.member.repository.WorkspaceMemberRepository;
import com.golapadeok.fluo.domain.role.domain.MemberRole;
import com.golapadeok.fluo.domain.role.domain.Role;
import com.golapadeok.fluo.domain.role.repository.MemberRoleRepository;
import com.golapadeok.fluo.domain.role.repository.RoleRepository;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AcceptInviteService {

    private final InvitationRepository invitationRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;
    private final RoleRepository roleRepository;
    private final MemberRoleRepository memberRoleRepository;

    @Transactional
    public InvitationAnswerResponse acceptInvitation(PrincipalDetails principalDetails, String invitationsId) {
        if (principalDetails == null || principalDetails.getMember() == null) {
            throw new IllegalArgumentException("로그인해야 합니다.");
        }

        Member member = principalDetails.getMember();

        Invitation invitation = this.invitationRepository.findById(Long.valueOf(invitationsId))
                .orElseThrow(() -> new InvitationException(InvitationErrorStatus.NOT_FOUND_INVITATION));

        WorkspaceMember workspaceMember = WorkspaceMember.builder()
                .member(member)
                .workspace(invitation.getWorkspace())
                .build();
        Long saved = this.workspaceMemberRepository.save(workspaceMember).getId();

        Role generateRole = this.roleRepository.findByNameAndWorkspaceId(invitation.getWorkspace().getId())
                .orElseThrow(() -> new InvitationException(InvitationErrorStatus.NOT_FOUND_ROLE));

        // 역할 부여
        giveGenerateRole(member, generateRole);

        String message;
        if(saved != null) {
            message = "가입을 성공했습니다.";
            invitation.updateIsPending(true); // 초대여부를 true로 변경하게 되면서 초대목록에 보여주지 않게 됨.
//            this.invitationRepository.delete(invitation); // 가입이 성공하면 초대 목록에서 제거
        }else{
            message = "가입을 실패했습니다.";
        }

        return new InvitationAnswerResponse(message);
    }

    private void giveGenerateRole(Member member, Role generateRole) {
        MemberRole memberRole = MemberRole.builder()
                .member(member)
                .role(generateRole)
                .build();
        this.memberRoleRepository.save(memberRole);
    }
}
