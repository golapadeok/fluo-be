//package com.golapadeok.fluo.domain.role.service;
//
//import com.golapadeok.fluo.common.annotation.AuthCheck;
//import com.golapadeok.fluo.domain.member.domain.Member;
//import com.golapadeok.fluo.domain.member.domain.WorkspaceMember;
//import com.golapadeok.fluo.domain.member.repository.MemberRepository;
//import com.golapadeok.fluo.domain.member.repository.WorkspaceMemberRepository;
//import com.golapadeok.fluo.domain.role.domain.MemberRole;
//import com.golapadeok.fluo.domain.role.domain.Role;
//import com.golapadeok.fluo.domain.role.exception.RoleErrorStatus;
//import com.golapadeok.fluo.domain.role.exception.RoleException;
//import com.golapadeok.fluo.domain.role.repository.MemberRoleQueryRepository;
//import com.golapadeok.fluo.domain.role.repository.MemberRoleRepository;
//import com.golapadeok.fluo.domain.role.repository.RoleRepository;
//import com.golapadeok.fluo.domain.workspace.domain.Workspace;
//import com.golapadeok.fluo.domain.workspace.repository.WorkspaceRepository;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.List;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//@Slf4j
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@SpringBootTest
//class RoleServiceTest {
//
//    @Autowired
//    private MemberRepository memberRepository;
//
//    @Autowired
//    private WorkspaceMemberRepository workspaceMemberRepository;
//
//    @Autowired
//    private WorkspaceRepository workspaceRepository;
//
//    @Autowired
//    private RoleRepository roleRepository;
//
//    @Autowired
//    private MemberRoleRepository memberRoleRepository;
//
//    @Autowired
//    private RoleService roleService;
//
//    @Autowired
//    private MemberRoleQueryRepository memberRoleQueryRepository;
//
//    @BeforeEach
//    public void init() {
//        // 워크스페이스 생성
//        Workspace workspace = new Workspace("title", "description", "url");
//        this.workspaceRepository.save(workspace);
//
//        // 멤버 생성
//        Member member = Member.builder().name("name").email("email").profile("profile").build();
//        this.memberRepository.save(member);
//
//        // 워크스페이스에 멤버 추가
//        WorkspaceMember workspaceMember = WorkspaceMember.builder().member(member).workspace(workspace).build();
//        this.workspaceMemberRepository.save(workspaceMember);
//    }
//
//    @Test
//    @Order(1)
//    @DisplayName("멤버가 관리자 역할을 갖고 있을 때 삭제가 안되는지 테스트")
//    public void testDeleteRole() {
//        // given
//        Long memberId = 1L;
//        Long workspaceId = 1L;
//        giveAdminRole(memberId, workspaceId);
//
//        // when
////        this.roleService.deleteWorkspaceRole(1);
//        Role role = this.roleRepository.findByNameAndWorkspaceId("관리자", workspaceId).get();
//
//        // then
//        assertThrows(RoleException.class, () ->
//                this.roleService.deleteWorkspaceRole(role.getId().intValue()));
//    }
//
//    @Test
//    @Order(2)
//    @DisplayName("멤버에게 역할이 부여됬을때 역할만 삭제되는지 확인")
//    public void testDeleteRoleWithMember(){
//        // given
//        Long memberId = 2L;
//        Long workspaceId = 2L;
//        giveGeneralRole(memberId, workspaceId);
//
//        // when
//        Role role = this.roleRepository.findByNameAndWorkspaceId("일반", workspaceId).get();
//        this.roleService.deleteWorkspaceRole(role.getId().intValue());
//
//        // then
//        assertThrows(RoleException.class, () ->
//                this.memberRoleRepository.findById(role.getId())
//                        .orElseThrow(() -> new RoleException(RoleErrorStatus.NOT_FOUND_ROLE))
//        );
//        assertThat(this.memberRepository.findById(role.getId())).isNotNull();
//        assertThrows(RoleException.class, () ->
//                this.roleRepository.findById(role.getId())
//                        .orElseThrow(() -> new RoleException(RoleErrorStatus.NOT_FOUND_ROLE))
//        );
//    }
//
//    @Test
//    @Order(3)
//    @DisplayName("워크스페이스에서 멤버가 추방됐을때 멤버에게 부여된 역할이 사라지는지 테스트")
//    public void testRemoveMemberWithRole() {
//        // given
//        Long memberId = 3L;
//        Long workspaceId = 3L;
//        giveGeneralRole(memberId, workspaceId);
//
//        // when
//        WorkspaceMember workspaceMember = workspaceMemberRepository.findByMemberIdAndWorkspaceId(memberId, workspaceId).get();
//        workspaceMemberRepository.delete(workspaceMember);
//
//        // 그룹에서 제외 했으니까 해당 유저의 역할을 제거해야함. -> 이 부분은 실제 로직에 포함되어야 함.
//        MemberRole memberRole = memberRoleQueryRepository.findWorkspaceWithMemberRoleList(memberId, workspaceId).get();
//        log.info("memberRole : {}", memberRole.getId());
//        memberRoleRepository.delete(memberRole);
//
//        // then
//        assertThat(this.memberRoleRepository.findById(memberRole.getId()).orElse(null)).isNull();
//    }
//
//    private void giveGeneralRole(Long memberId, Long workspaceId) {
//        // 워크스페이스 역할 생성
//        Workspace workspace = workspaceRepository.findById(workspaceId).get();
//        Member member = memberRepository.findById(memberId).get();
//        Role role = Role.builder().name("일반").roles(
//                        "DELETE_WORKSPACE")
//                .workspace(workspace)
//                .build();
//        roleRepository.save(role);
//        // 멤버에게 역할 부여
//        MemberRole memberRole = MemberRole.builder().member(member).role(role).build();
//        memberRoleRepository.save(memberRole);
//    }
//
//    private void giveAdminRole(Long memberId, Long workspaceId) {
//        // 워크스페이스 역할 생성
//        Workspace workspace = workspaceRepository.findById(workspaceId).get();
//        Member member = memberRepository.findById(memberId).get();
//        Role role = Role.builder().name("관리자").roles(
//                        "DELETE_WORKSPACE," +
//                                "INVITE_MEMBER," +
//                                "REMOVE_MEMBER," +
//                                "CREATE_ROLE," +
//                                "MODIFY_ROLE," +
//                                "DELETE_ROLE," +
//                                "ASSIGN_ROLE," +
//                                "CREATE_TASK," +
//                                "MODIFY_TASK," +
//                                "DELETE_TASK")
//                .workspace(workspace)
//                .build();
//        roleRepository.save(role);
//        // 멤버에게 역할 부여
//        MemberRole memberRole = MemberRole.builder().member(member).role(role).build();
//        memberRoleRepository.save(memberRole);
//    }
//
//}