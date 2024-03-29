package com.golapadeok.fluo.domain.invitation.service;

import com.golapadeok.fluo.common.security.domain.PrincipalDetails;
import com.golapadeok.fluo.domain.invitation.dto.response.InvitationAnswerResponse;
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
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
//@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class MemberInviteCodeServiceTest {

    @Autowired
    private MemberInviteCodeService memberInviteCodeService;

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
//        workspace.createInvitationCode("123456");
        this.workspaceRepository.save(workspace);

        Member member = Member.builder().name("name").email("email@test.com").profile("profile").build();
        this.memberRepository.save(member);
    }

    @Test
    @Order(1)
    @DisplayName("초대코드로 가입되는지 테스트")
    public void testInviteCode() {
        // given
        Member member = memberRepository.findById(1L).get();
        PrincipalDetails principalDetails = new PrincipalDetails(member);
        String invitationsCode = "123456";

        // when
        InvitationAnswerResponse invitationAnswerResponse = this.memberInviteCodeService.saveMemberInviteCode(principalDetails, invitationsCode);

        // then
        assertThat(invitationAnswerResponse.getMessage()).isEqualTo("가입을 성공했습니다.");
        assertThat(this.workspaceMemberRepository.findById(1L).get().getMember().getId()).isEqualTo(1L);
    }

}