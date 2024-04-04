//package com.golapadeok.fluo.domain.invitation.service;
//
//import com.golapadeok.fluo.common.security.domain.PrincipalDetails;
//import com.golapadeok.fluo.domain.invitation.domain.Invitation;
//import com.golapadeok.fluo.domain.invitation.dto.request.CursorPageRequest;
//import com.golapadeok.fluo.domain.invitation.dto.response.MemberInvitationListResponse;
//import com.golapadeok.fluo.domain.invitation.repository.InvitationRepository;
//import com.golapadeok.fluo.domain.member.domain.Member;
//import com.golapadeok.fluo.domain.member.repository.MemberRepository;
//import com.golapadeok.fluo.domain.workspace.domain.Workspace;
//import com.golapadeok.fluo.domain.workspace.repository.WorkspaceRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.List;
//
//@Slf4j
//@SpringBootTest
//class MemberInvitationListServiceTest {
//
//    @Autowired
//    private MemberInviteListService memberInvitationListService;
//
//    @Autowired
//    private WorkspaceRepository workspaceRepository;
//
//    @Autowired
//    private InvitationRepository invitationRepository;
//
//    @Autowired
//    private MemberRepository memberRepository;
//
//    @BeforeEach
//    public void init() {
//        List<Workspace> workspaces = Arrays.asList(
//                new Workspace("title1", "description1", "11"),
//                new Workspace("title2", "description2", "11"),
//                new Workspace("title3", "description3", "11"),
//                new Workspace("title4", "description4", "11"),
//                new Workspace("title5", "description5", "11"),
//                new Workspace("title6", "description6", "11"),
//                new Workspace("title7", "description7", "11"),
//                new Workspace("title8", "description8", "11"),
//                new Workspace("title9", "description9", "11"),
//                new Workspace("title10", "description10", "11"),
//                new Workspace("title11", "description11", "11")
//        );
//        this.workspaceRepository.saveAll(workspaces);
//
//        // Member 생성 및 저장
//        List<Member> members = Arrays.asList(
//                Member.builder().name("이름1").email("이메일1").profile("프로필1").build(),
//                Member.builder().name("이름2").email("이메일2").profile("프로필2").build(),
//                Member.builder().name("이름3").email("이메일3").profile("프로필3").build()
//        );
//        memberRepository.saveAll(members);
//
//        LocalDateTime now = LocalDateTime.now();
//
//        List<Invitation> invitations = Arrays.asList(
//                Invitation.builder().member(members.get(0)).workspace(workspaces.get(0)).createDate(now.plusMinutes(1)).build(),
//                Invitation.builder().member(members.get(0)).workspace(workspaces.get(1)).createDate(now.plusMinutes(2)).build(),
//                Invitation.builder().member(members.get(0)).workspace(workspaces.get(2)).createDate(now.plusMinutes(3)).build(),
//                Invitation.builder().member(members.get(0)).workspace(workspaces.get(3)).createDate(now.plusMinutes(4)).build(),
//                Invitation.builder().member(members.get(0)).workspace(workspaces.get(4)).createDate(now.plusMinutes(5)).build(),
//                Invitation.builder().member(members.get(0)).workspace(workspaces.get(5)).createDate(now.plusMinutes(6)).build(),
//                Invitation.builder().member(members.get(0)).workspace(workspaces.get(6)).createDate(now.plusMinutes(7)).build(),
//                Invitation.builder().member(members.get(0)).workspace(workspaces.get(7)).createDate(now.plusMinutes(8)).build(),
//                Invitation.builder().member(members.get(0)).workspace(workspaces.get(8)).createDate(now.plusMinutes(9)).build(),
//                Invitation.builder().member(members.get(0)).workspace(workspaces.get(9)).createDate(now.plusMinutes(10)).build(),
//                Invitation.builder().member(members.get(0)).workspace(workspaces.get(10)).createDate(now.plusMinutes(11)).build()
//        );
//        invitationRepository.saveAll(invitations);
//    }
//
//    @Test
//    @DisplayName("멤버 초대 목록 페이징 확인")
//    public void testInvitation() {
//        // given
//        Member member = Member.builder().id(1L).name("이름1").email("이메일1").profile("프로필1").build();
//        PrincipalDetails principalDetails = new PrincipalDetails(member);
//        CursorPageRequest cursorPageRequest = new CursorPageRequest(7, 5);
//
//        // when
//        MemberInvitationListResponse response = memberInvitationListService.getInvitationList(principalDetails, cursorPageRequest);
//
//        // then
//        log.info("response : {}", response);
//    }
//
//}