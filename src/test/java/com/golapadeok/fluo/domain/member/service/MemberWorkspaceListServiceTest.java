//package com.golapadeok.fluo.domain.member.service;
//
//import com.golapadeok.fluo.common.security.domain.PrincipalDetails;
//import com.golapadeok.fluo.domain.member.domain.Member;
//import com.golapadeok.fluo.domain.member.domain.WorkspaceMember;
//import com.golapadeok.fluo.domain.member.dto.request.CursorPageRequest;
//import com.golapadeok.fluo.domain.member.dto.response.MemberWorkspaceListResponse;
//import com.golapadeok.fluo.domain.member.repository.MemberRepository;
//import com.golapadeok.fluo.domain.member.repository.WorkspaceMemberRepository;
//import com.golapadeok.fluo.domain.workspace.domain.Workspace;
//import com.golapadeok.fluo.domain.workspace.repository.WorkspaceRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.Arrays;
//import java.util.List;
//
//@Slf4j
////@Transactional
//@SpringBootTest
//class MemberWorkspaceListServiceTest {
//
//    @Autowired
//    private MemberWorkspaceListService memberWorkspaceListService;
//
//    @Autowired
//    private MemberRepository memberRepository;
//
//    @Autowired
//    private WorkspaceRepository workspaceRepository;
//
//    @Autowired
//    private WorkspaceMemberRepository workspaceMemberRepository;
//
//    @BeforeEach
//    public void init() {
//        // Workspace 생성 및 저장
//        List<Workspace> workspaces = Arrays.asList(
//                new Workspace("title", "description", "url"),
//                new Workspace("title1", "description1", "url"),
//                new Workspace("title2", "description2", "url"),
//                new Workspace("title3", "description3", "url"),
//                new Workspace("title4", "description4", "url"),
//                new Workspace("title5", "description5", "url"),
//                new Workspace("title6", "description6", "url"),
//                new Workspace("title7", "description7", "url"),
//                new Workspace("title8", "description8", "url"),
//                new Workspace("title9", "description9", "url"),
//                new Workspace("title10", "description10", "url")
//        );
//        workspaceRepository.saveAll(workspaces);
//
//        // Member 생성 및 저장
//        List<Member> members = Arrays.asList(
//                Member.builder().name("이름1").email("이메일1").profile("프로필1").build(),
//                Member.builder().name("이름2").email("이메일2").profile("프로필2").build(),
//                Member.builder().name("이름3").email("이메일3").profile("프로필3").build()
//        );
//        memberRepository.saveAll(members);
//
//        // WorkspaceMember 생성 및 저장
//        List<WorkspaceMember> workspaceMembers = Arrays.asList(
//                WorkspaceMember.builder().member(members.get(0)).workspace(workspaces.get(0)).build(),
//                WorkspaceMember.builder().member(members.get(1)).workspace(workspaces.get(1)).build(),
//                WorkspaceMember.builder().member(members.get(2)).workspace(workspaces.get(2)).build(),
//                WorkspaceMember.builder().member(members.get(0)).workspace(workspaces.get(3)).build(),
//                WorkspaceMember.builder().member(members.get(0)).workspace(workspaces.get(4)).build(),
//                WorkspaceMember.builder().member(members.get(0)).workspace(workspaces.get(5)).build(),
//                WorkspaceMember.builder().member(members.get(0)).workspace(workspaces.get(6)).build(),
//                WorkspaceMember.builder().member(members.get(0)).workspace(workspaces.get(7)).build(),
//                WorkspaceMember.builder().member(members.get(2)).workspace(workspaces.get(8)).build(),
//                WorkspaceMember.builder().member(members.get(2)).workspace(workspaces.get(9)).build(),
//                WorkspaceMember.builder().member(members.get(0)).workspace(workspaces.get(10)).build(),
//                WorkspaceMember.builder().member(members.get(2)).workspace(workspaces.get(1)).build()
//        );
//        workspaceMemberRepository.saveAll(workspaceMembers);
//    }
//
//    @Test
//    @DisplayName("멤버가 소속된 워크스페이스 조회")
//    public void testIncludeWorkspace() {
//
//        Member member = Member.builder()
//                .id(1L)
//                .name("이름")
//                .email("이메일")
//                .profile("프로필")
//                .build();
//        // given
//        PrincipalDetails principalDetails = new PrincipalDetails(member);
//        CursorPageRequest cursorPageRequest = new CursorPageRequest(0, 5);
//
//        // when
//        MemberWorkspaceListResponse workspaceList = this.memberWorkspaceListService.getWorkspaceList(principalDetails, cursorPageRequest);
//
//        // then
//        log.info(workspaceList.toString());
////        assertThat(workspaceList.getItems().size()).isEqualTo(5);
////        assertThat(workspaceList.getItems()).isNotNull();
//    }
//
//}