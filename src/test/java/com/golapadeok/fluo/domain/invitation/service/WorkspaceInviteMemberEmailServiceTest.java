/*
package com.golapadeok.fluo.domain.invitation.service;

import com.golapadeok.fluo.common.security.domain.PrincipalDetails;
import com.golapadeok.fluo.domain.invitation.domain.Invitation;
import com.golapadeok.fluo.domain.invitation.dto.request.CursorPageRequest;
import com.golapadeok.fluo.domain.invitation.dto.request.InviteEmailRequest;
import com.golapadeok.fluo.domain.invitation.dto.response.InvitationEmailResponse;
import com.golapadeok.fluo.domain.invitation.dto.response.MemberInvitationListResponse;
import com.golapadeok.fluo.domain.invitation.repository.InvitationQueryRepository;
import com.golapadeok.fluo.domain.invitation.repository.InvitationRepository;
import com.golapadeok.fluo.domain.member.domain.Member;
import com.golapadeok.fluo.domain.member.repository.MemberRepository;
import com.golapadeok.fluo.domain.member.repository.WorkspaceMemberRepository;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import com.golapadeok.fluo.domain.workspace.repository.WorkspaceRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Slf4j
@SpringBootTest
class WorkspaceInviteMemberEmailServiceTest {


    @Autowired
    private WorkspaceInviteMemberEmailService workspaceInviteMemberEmailService;

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Autowired
    private InvitationRepository invitationRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private WorkspaceMemberRepository workspaceMemberRepository;

    @Autowired
    private InvitationQueryRepository invitationQueryRepository;

    @BeforeEach
    public void init() {
        Workspace workspace = new Workspace("title", "description", "url");
        this.workspaceRepository.save(workspace);

        Member member = Member.builder().name("name").email("email@test.com").profile("profile").build();
        this.memberRepository.save(member);
    }

    @Test
    @Order(1)
    @DisplayName("회원의 이메일을 입력시 초대목록에 생기는지 테스트")
    public void testTransferEmailWithList() {
        // given
        String email = "email@test.com";
        String workspaceId = "1";
        InviteEmailRequest inviteEmailRequest = new InviteEmailRequest(email);

        // when
        InvitationEmailResponse invitationEmailResponse = this.workspaceInviteMemberEmailService.InviteMemberEmail(workspaceId, inviteEmailRequest);

        // then
        log.info("invitation : {}", invitationEmailResponse);
        assertThat(this.invitationRepository.findById(1L)).isNotNull();
        assertThat(this.invitationRepository.findById(1L).get().getMember().getId()).isEqualTo(1L);
        assertThat(this.invitationRepository.findById(1L).get().getWorkspace().getId()).isEqualTo(1L);
    }

    @Test
    @Order(2)
    @DisplayName("회원의 이메일 입력시 회원 쪽 초대목록에 출력되는지 테스트")
    public void testPrintMemberInviteList() {
        // given
        Member member = this.memberRepository.findById(2L).get();
        Workspace workspace = this.workspaceRepository.findById(2L).get();
        Invitation saved = this.invitationRepository.save(Invitation.builder().member(member).workspace(workspace).build());

        CursorPageRequest cursorPageRequest = new CursorPageRequest(0, 5);

        // when
        Page<Invitation> invitations = invitationQueryRepository.findMemberWithInvitationList(member.getId(), cursorPageRequest);

        // then
        assertThat(invitations.getContent().size()).isEqualTo(1);

    }

}*/
