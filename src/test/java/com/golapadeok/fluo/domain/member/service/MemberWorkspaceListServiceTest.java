package com.golapadeok.fluo.domain.member.service;

import com.golapadeok.fluo.common.security.domain.PrincipalDetails;
import com.golapadeok.fluo.domain.member.domain.Member;
import com.golapadeok.fluo.domain.member.domain.WorkspaceMember;
import com.golapadeok.fluo.domain.member.dto.response.MemberWorkspaceListResponse;
import com.golapadeok.fluo.domain.member.repository.MemberRepository;
import com.golapadeok.fluo.domain.member.repository.WorkspaceMemberRepository;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import com.golapadeok.fluo.domain.workspace.repository.WorkspaceRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Size;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;

import static com.mysema.commons.lang.Assert.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
//@Transactional
@SpringBootTest
class MemberWorkspaceListServiceTest {

    @Autowired
    private MemberWorkspaceListService memberWorkspaceListService;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Autowired
    private WorkspaceMemberRepository workspaceMemberRepository;

    @BeforeEach
    public void init() {
        // Workspace 생성 및 저장
        List<Workspace> workspaces = Arrays.asList(
                new Workspace("title", "description"),
                new Workspace("title1", "description1"),
                new Workspace("title2", "description2"),
                new Workspace("title3", "description3"),
                new Workspace("title4", "description4"),
                new Workspace("title5", "description5"),
                new Workspace("title6", "description6"),
                new Workspace("title7", "description7"),
                new Workspace("title8", "description8"),
                new Workspace("title9", "description9"),
                new Workspace("title10", "description10")
        );
        workspaceRepository.saveAll(workspaces);

        // Member 생성 및 저장
        List<Member> members = Arrays.asList(
                Member.builder().name("이름1").email("이메일1").profile("프로필1").build(),
                Member.builder().name("이름2").email("이메일2").profile("프로필2").build(),
                Member.builder().name("이름3").email("이메일3").profile("프로필3").build()
        );
        memberRepository.saveAll(members);

        // WorkspaceMember 생성 및 저장
        List<WorkspaceMember> workspaceMembers = Arrays.asList(
                WorkspaceMember.builder().member(members.get(0)).workspace(workspaces.get(0)).build(),
                WorkspaceMember.builder().member(members.get(1)).workspace(workspaces.get(1)).build(),
                WorkspaceMember.builder().member(members.get(2)).workspace(workspaces.get(2)).build(),
                WorkspaceMember.builder().member(members.get(0)).workspace(workspaces.get(3)).build(),
                WorkspaceMember.builder().member(members.get(0)).workspace(workspaces.get(4)).build(),
                WorkspaceMember.builder().member(members.get(0)).workspace(workspaces.get(5)).build(),
                WorkspaceMember.builder().member(members.get(0)).workspace(workspaces.get(6)).build(),
                WorkspaceMember.builder().member(members.get(0)).workspace(workspaces.get(7)).build(),
                WorkspaceMember.builder().member(members.get(2)).workspace(workspaces.get(8)).build(),
                WorkspaceMember.builder().member(members.get(2)).workspace(workspaces.get(9)).build(),
                WorkspaceMember.builder().member(members.get(0)).workspace(workspaces.get(10)).build(),
                WorkspaceMember.builder().member(members.get(2)).workspace(workspaces.get(1)).build()
        );
        workspaceMemberRepository.saveAll(workspaceMembers);
    }

    @Test
    @DisplayName("멤버가 소속된 워크스페이스 조회")
    public void testIncludeWorkspace() {

        Member member = Member.builder()
                .id(1L)
                .name("이름")
                .email("이메일")
                .profile("프로필")
                .build();
        // given
        PrincipalDetails principalDetails = new PrincipalDetails(member);
        Long cursorId = null;
        Integer limit = null;

        // when
        MemberWorkspaceListResponse workspaceList = this.memberWorkspaceListService.getWorkspaceList(principalDetails, cursorId);

        // then
        log.info(workspaceList.toString());
//        assertThat(workspaceList.getItems().size()).isEqualTo(5);
//        assertThat(workspaceList.getItems()).isNotNull();
    }

}