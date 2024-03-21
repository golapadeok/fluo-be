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
        Workspace workspace = new Workspace("title", "description");
        Workspace workspace1 = new Workspace("title1", "description1");
        workspaceRepository.save(workspace);
        workspaceRepository.save(workspace1);

        Member member1 = Member.builder()
                .name("이름1")
                .email("이메일1")
                .profile("프로필1")
                .build();
        memberRepository.save(member1);

        Member member2 = Member.builder()
                .name("이름2")
                .email("이메일2")
                .profile("프로필2")
                .build();
        memberRepository.save(member2);

        Member member3 = Member.builder()
                .name("이름3")
                .email("이메일3")
                .profile("프로필3")
                .build();
        memberRepository.save(member3);

        WorkspaceMember workspaceMember1 = WorkspaceMember.builder()
                .member(member1)
                .workspace(workspace)
                .build();
        WorkspaceMember workspaceMember2 = WorkspaceMember.builder()
                .member(member2)
                .workspace(workspace)
                .build();
        WorkspaceMember workspaceMember3 = WorkspaceMember.builder()
                .member(member1)
                .workspace(workspace1)
                .build();
        WorkspaceMember workspaceMember4 = WorkspaceMember.builder()
                .member(member3)
                .workspace(workspace1)
                .build();


        workspaceMemberRepository.save(workspaceMember1);
        workspaceMemberRepository.save(workspaceMember2);
        workspaceMemberRepository.save(workspaceMember3);
        workspaceMemberRepository.save(workspaceMember4);
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
        String cursorId = null;
        String limit = null;

        // when
        MemberWorkspaceListResponse workspaceList = this.memberWorkspaceListService.getWorkspaceList(principalDetails, cursorId, limit);

        // then
        log.info(workspaceList.toString());
        assertThat(workspaceList.getItems().size()).isEqualTo(2);
        assertThat(workspaceList.getItems()).isNotNull();
    }

}