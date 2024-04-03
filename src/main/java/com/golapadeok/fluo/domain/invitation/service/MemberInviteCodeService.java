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

    private static final List<String> DEFAULT_CREDENTIAL = List.of(
            "CREATE_TASK",
            "MODIFY_TASK",
            "DELETE_TASK"
    );

    @Transactional
    public InvitationAnswerResponse saveMemberInviteCode(PrincipalDetails principalDetails, String invitationsCode) {
        if (principalDetails == null || principalDetails.getMember() == null) {
            throw new IllegalArgumentException("로그인해야 합니다.");
        }

        Member member = principalDetails.getMember();

        Workspace workspace = this.workspaceRepository.findByInvitationCode(invitationsCode)
                .orElseThrow(() -> new InvitationException(InvitationErrorStatus.INVALID_INVITATION_CODE));

        WorkspaceMember workspaceMember = WorkspaceMember.builder()
                .member(member)
                .workspace(workspace)
                .build();

        // 역할 생성
        Role generateRole = createGenerateRole(workspace);

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

    private Role createGenerateRole(Workspace workspace) {
        String credential = String.join(",", DEFAULT_CREDENTIAL);
        Role generateRole = Role.builder()
                .name("일반")
                .description("일반 역할입니다.")
                .roles(credential)
                .workspace(workspace)
                .isDefault(true)
                .build();
        this.roleRepository.save(generateRole);
        return generateRole;
    }

}
