package com.golapadeok.fluo.domain.workspace.service;

import com.golapadeok.fluo.common.security.domain.PrincipalDetails;
import com.golapadeok.fluo.domain.member.domain.Member;
import com.golapadeok.fluo.domain.member.repository.MemberRepository;
import com.golapadeok.fluo.domain.role.domain.Role;
import com.golapadeok.fluo.domain.role.repository.MemberRoleQueryRepository;
import com.golapadeok.fluo.domain.role.repository.MemberRoleRepository;
import com.golapadeok.fluo.domain.role.repository.RoleRepository;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import com.golapadeok.fluo.domain.workspace.dto.request.WorkspaceGrantRoleRequest;
import com.golapadeok.fluo.domain.workspace.dto.response.WorkspaceGrantRoleResponse;
import com.golapadeok.fluo.domain.workspace.repository.WorkspaceRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class WorkspaceGrantRoleServiceTest {

    @Autowired
    private WorkspaceGrantRoleService workspaceGrantRoleService;

    @Autowired
    private WorkspaceRepository workspaceRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private MemberRoleRepository memberRoleRepository;

    @Autowired
    private MemberRoleQueryRepository memberRoleQueryRepository;

    @BeforeEach
    public void init() {
        Workspace workspace = new Workspace("title", "description", "url");
        this.workspaceRepository.save(workspace);

        Member member = Member.builder().name("name").email("email").profile("profile").build();
        this.memberRepository.save(member);

        Role role = Role.builder().name("역할").roles("DELETE_WORKSPACE").workspace(workspace).build();
        this.roleRepository.save(role);
    }

    @Test
    @Order(1)
    @DisplayName("멤버 역할 부여 서비스 성공 테스트")
    public void testGrantRole() {
        // given
        Member member = this.memberRepository.findById(1L).get();
        PrincipalDetails principalDetails = new PrincipalDetails(member);
        WorkspaceGrantRoleRequest request = new WorkspaceGrantRoleRequest(new WorkspaceGrantRoleRequest.RoleInfo("1", "DELETE_WORKSPACE"));

        // when
        WorkspaceGrantRoleResponse workspaceGrantRoleResponse = this.workspaceGrantRoleService.grantRole(principalDetails, request);

        // then
        log.info("response : {}", workspaceGrantRoleResponse);
        assertThat(workspaceGrantRoleResponse).isNotNull();

    }

    @Test
    @Order(2)
    @DisplayName("멤버에게 역할이 정상적으로 들어갔는지 확인")
    public void testGrantRoleWithMember() {
        // given
        Member member = this.memberRepository.findById(2L).get();
        PrincipalDetails principalDetails = new PrincipalDetails(member);
        WorkspaceGrantRoleRequest request = new WorkspaceGrantRoleRequest(new WorkspaceGrantRoleRequest.RoleInfo("2", "DELETE_WORKSPACE"));

        // when
        WorkspaceGrantRoleResponse workspaceGrantRoleResponse = this.workspaceGrantRoleService.grantRole(principalDetails, request);

        // then
        assertThat(this.memberRoleRepository.findById(2L).get()).isNotNull();
        assertThat(this.memberRoleRepository.findById(2L).get().getMember().getId()).isEqualTo(2L);
    }


}