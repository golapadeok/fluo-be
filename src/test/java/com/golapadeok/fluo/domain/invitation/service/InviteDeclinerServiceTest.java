/*
package com.golapadeok.fluo.domain.invitation.service;

import com.golapadeok.fluo.domain.invitation.domain.Invitation;
import com.golapadeok.fluo.domain.invitation.dto.response.InvitationAnswerResponse;
import com.golapadeok.fluo.domain.invitation.exception.InvitationErrorStatus;
import com.golapadeok.fluo.domain.invitation.exception.InvitationException;
import com.golapadeok.fluo.domain.invitation.repository.InvitationRepository;
import com.golapadeok.fluo.domain.member.domain.Member;
import com.golapadeok.fluo.domain.member.repository.MemberRepository;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import com.golapadeok.fluo.domain.workspace.repository.WorkspaceRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@Slf4j
@SpringBootTest
class InviteDeclinerServiceTest {

    @Autowired
    private InviteDeclinerService inviteDeclinerService;

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Autowired
    private InvitationRepository invitationRepository;

    @Autowired
    private MemberRepository memberRepository;

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
    @DisplayName("초대 거절시 출력 메시지 확인")
    public void testDeclinerInvite() {
        // given
        Long invitationId = 1L;

        // when
        InvitationAnswerResponse invitationAnswerResponse = this.inviteDeclinerService.declinerInvitation(String.valueOf(invitationId));

        // then
        assertThat(invitationAnswerResponse.getMessage()).isEqualTo("초대를 거절했습니다.");
    }

    @Test
    @DisplayName("초대 거절시 초대목록 제거 확인")
    public void testDeclinerInviteWithInvitationList() {
        // given
        Long invitationId = 2L;

        // when
        InvitationAnswerResponse invitationAnswerResponse = this.inviteDeclinerService.declinerInvitation(String.valueOf(invitationId));

        // then
        assertThrows(InvitationException.class, () -> {
            this.invitationRepository.findById(2L)
                    .orElseThrow(() -> new InvitationException(InvitationErrorStatus.NOT_FOUND_INVITATION));
        });
    }

}*/
