package com.golapadeok.fluo.common.annotation.interception;

import com.golapadeok.fluo.domain.member.domain.Member;
import com.golapadeok.fluo.domain.member.domain.WorkspaceMember;
import com.golapadeok.fluo.domain.member.repository.MemberRepository;
import com.golapadeok.fluo.domain.member.repository.WorkspaceMemberRepository;
import com.golapadeok.fluo.domain.role.domain.MemberRole;
import com.golapadeok.fluo.domain.role.domain.Role;
import com.golapadeok.fluo.domain.role.dto.request.RoleCreateRequest;
import com.golapadeok.fluo.domain.role.dto.response.CreateRoleResponse;
import com.golapadeok.fluo.domain.role.repository.MemberRoleQueryRepository;
import com.golapadeok.fluo.domain.role.repository.MemberRoleRepository;
import com.golapadeok.fluo.domain.role.repository.RoleRepository;
import com.golapadeok.fluo.domain.role.service.RoleService;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import com.golapadeok.fluo.domain.workspace.repository.WorkspaceRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class AuthCheckInterceptorTest {

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberRoleRepository memberRoleRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private WorkspaceMemberRepository workspaceMemberRepository;

    @Autowired
    private MemberRoleQueryRepository memberRoleQueryRepository;

    @BeforeEach
    public void init() {
        // 워크스페이스 생성
        Workspace workspace = new Workspace("title", "description", "url");
        workspaceRepository.save(workspace);

        // 워크스페이스에 역할 생성
        RoleCreateRequest request = new RoleCreateRequest("roleName", Arrays.asList("ENTER"));
        CreateRoleResponse workspaceRole = roleService.createWorkspaceRole(workspace.getId().intValue(), request);

        // 멤버 생성
        Member member = Member.builder()
                .name("이름")
                .email("이메일")
                .profile("사진")
                .build();
        memberRepository.save(member);

        // 워크스페이스에 멤버 소속 시키기
        WorkspaceMember workspaceMember = WorkspaceMember.builder()
                .member(member)
                .workspace(workspace)
                .build();
        workspaceMemberRepository.save(workspaceMember);

        // 역할 아이디 찾기
        Role role = roleRepository.findById(Long.valueOf(workspaceRole.getRoleId())).orElse(null);

        // 멤버에게 역할 부여하기
        MemberRole memberRole = MemberRole.builder()
                .member(member)
                .role(role)
                .build();
        memberRoleRepository.save(memberRole);
    }

    @Test
    @DisplayName("멤버가 워크스페이스에서 부여받은 역할 조회")
    public void testSelectWorkspaceRole() {
        // given
        Long memberId = 1L;
        Long workspaceId = 1L;

        // when
        MemberRole memberRole = memberRoleQueryRepository.findByMemberIdAndWorkspaceId(memberId, workspaceId).get();

        // then
        log.info("memberRole : {}", memberRole);
        assertThat(memberRole).isNotNull();
        assertThat(memberRole.getRole().getRoles()).isEqualTo("ENTER");
    }


}