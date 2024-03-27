package com.golapadeok.fluo.domain.invitation.service;

import com.golapadeok.fluo.common.security.domain.PrincipalDetails;
import com.golapadeok.fluo.domain.invitation.domain.Invitation;
import com.golapadeok.fluo.domain.invitation.dto.response.InvitationAnswerResponse;
import com.golapadeok.fluo.domain.invitation.exception.InvitationErrorStatus;
import com.golapadeok.fluo.domain.invitation.exception.InvitationException;
import com.golapadeok.fluo.domain.invitation.repository.InvitationRepository;
import com.golapadeok.fluo.domain.member.domain.Member;
import com.golapadeok.fluo.domain.member.domain.WorkspaceMember;
import com.golapadeok.fluo.domain.member.repository.MemberRepository;
import com.golapadeok.fluo.domain.member.repository.WorkspaceMemberRepository;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import com.golapadeok.fluo.domain.workspace.repository.WorkspaceRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@Slf4j
@SpringBootTest
class AcceptInviteServiceTest {

    @Autowired
    private AcceptInviteService acceptInviteService;

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Autowired
    private InvitationRepository invitationRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private WorkspaceMemberRepository workspaceMemberRepository;

    @BeforeEach
    public void init() {
        Workspace workspace = new Workspace("title", "description", "url");
        this.workspaceRepository.save(workspace);

        Member member = Member.builder().name("name").email("emial").profile("profile").build();
        this.memberRepository.save(member);

        Invitation invitation = Invitation.builder().member(member).workspace(workspace).build();
        this.invitationRepository.save(invitation);

    }

    @Test
    @DisplayName("초대를 수락하면 워크스페이스 멤버 추가")
    public void testAcceptInvitation() {
        // given
        Member member = Member.builder().id(1L).name("name").email("email").profile("profile").build();
        PrincipalDetails principalDetails = new PrincipalDetails(member);
        Long invitationId = 1L;

        // when
        InvitationAnswerResponse invitationAnswerResponse = this.acceptInviteService.acceptInvitation(principalDetails, String.valueOf(invitationId));
        log.info("invitationAnswerResponse : {}", invitationAnswerResponse);

        // then
        List<WorkspaceMember> workspaceMembers = this.workspaceMemberRepository.findByWorkspaceId(1L).get();
        log.info("workspaceMember : {}", workspaceMembers);
        assertThat(workspaceMembers.get(0).getMember().getId()).isEqualTo(1L);

    }

    @Test
    @DisplayName("초대를 수락하면 초대 목록 삭제")
    public void testAcceptInvitationWithInviteList() {
        // given
        Member member = Member.builder().id(1L).name("name").email("email").profile("profile").build();
        PrincipalDetails principalDetails = new PrincipalDetails(member);
        Long invitationId = 2L;

        // when
        InvitationAnswerResponse invitationAnswerResponse = this.acceptInviteService.acceptInvitation(principalDetails, String.valueOf(invitationId));
        log.info("invitationAnswerResponse : {}", invitationAnswerResponse);

        // then
        assertThrows(InvitationException.class, () -> {
            this.invitationRepository.findById(2L)
                    .orElseThrow(() -> new InvitationException(InvitationErrorStatus.NOT_FOUND_INVITATION));
        });
    }


}