/*
package com.golapadeok.fluo.domain.workspace.service;

import com.golapadeok.fluo.common.security.domain.PrincipalDetails;
import com.golapadeok.fluo.domain.member.domain.Member;
import com.golapadeok.fluo.domain.member.domain.WorkspaceMember;
import com.golapadeok.fluo.domain.member.repository.WorkspaceMemberRepository;
import com.golapadeok.fluo.domain.role.domain.MemberRole;
import com.golapadeok.fluo.domain.role.domain.Role;
import com.golapadeok.fluo.domain.role.repository.MemberRoleRepository;
import com.golapadeok.fluo.domain.role.repository.RoleRepository;
import com.golapadeok.fluo.domain.state.domain.State;
import com.golapadeok.fluo.domain.state.repository.StateRepository;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import com.golapadeok.fluo.domain.workspace.dto.request.WorkspaceCreateRequest;
import com.golapadeok.fluo.domain.workspace.dto.response.WorkspaceResponse;
import com.golapadeok.fluo.domain.workspace.repository.WorkspaceRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@ExtendWith(MockitoExtension.class)
class WorkspaceTest {
    private static final String DEFAULT_IMAGE = "https://fluo-bucket.s3.ap-northeast-2.amazonaws.com/images/default_ea81d464-2433-4ca2-9281-691187752a05_S88da226f2926405ba7abe131b6e55a90w.png";

    private static final List<String> DEFAULT_CREDENTIAL = List.of(
            "DELETE_WORKSPACE", "INVITE_MEMBER", "REMOVE_MEMBER", "CREATE_ROLE", "MODIFY_ROLE",
            "DELETE_ROLE", "ASSIGN_ROLE", "CREATE_TASK", "MODIFY_TASK", "DELETE_TASK"
    );


    @Nested
    @DisplayName("워크스페이스 생성 테스트")
    class Create {
        @InjectMocks
        private WorkspaceCreateService workspaceCreateService;

        @Mock
        private RoleRepository roleRepository;
        @Mock
        private MemberRoleRepository memberRoleRepository;
        @Mock
        private WorkspaceMemberRepository workspaceMemberRepository;
        @Mock
        private WorkspaceRepository workspaceRepository;
        @Mock
        private StateRepository stateRepository;


        @Test
        @DisplayName("워크스페이스 생성 성공 케이스")
        void create() {
            //given
            Member member = createMember();
            PrincipalDetails principal = createPrincipal(member);
            Workspace workspace = createWorkspace(principal.getMember());
            List<State> states = createStates(workspace);
            Role role = createRole(workspace);
            WorkspaceMember workspaceMember = createWorkspaceMember(member, workspace);
            MemberRole memberRole = createMemberRole(member, role);
            WorkspaceCreateRequest createRequest = createCreateRequest();

            BDDMockito.given(workspaceRepository.save(BDDMockito.any())).willReturn(workspace);
            BDDMockito.given(stateRepository.saveAll(BDDMockito.any())).willReturn(states);
            BDDMockito.given(workspaceMemberRepository.save(workspaceMember)).willReturn(workspaceMember);
            BDDMockito.given(roleRepository.save(role)).willReturn(role);
            BDDMockito.given(memberRoleRepository.save(memberRole)).willReturn(memberRole);
            BDDMockito.given(workspaceRepository.existsByInvitationCode("b04Icl")).willReturn(false);


            //when
            WorkspaceResponse response = workspaceCreateService.create(principal, createRequest);

            //then
        }

        private WorkspaceCreateRequest createCreateRequest() {
            return new WorkspaceCreateRequest("title", "description");
        }
    }

    private static PrincipalDetails createPrincipal(Member member) {
        return new PrincipalDetails(member);
    }

    private static Workspace createWorkspace(Member member) {
        Workspace workspace = new Workspace("title", "description", DEFAULT_IMAGE, member.getName());
        workspace.changeInvitationCode("b04Icl");
        return workspace;
    }

    private static Member createMember() {
        return new Member(1L, "member@gmail.com", "member1", "profile", "refresh", null, null);
    }

    private static Role createRole(Workspace workspace) {
        return new Role("관리자", "관리자 역할입니다.", String.join(",", DEFAULT_CREDENTIAL), workspace);
    }

    private static List<State> createStates(Workspace workspace) {
        List<State> states = List.of(new State("To Do", true), new State("In Progress", true), new State("Done", true));
        states.forEach(state -> state.changeWorkspace(workspace));
        return states;
    }

    private static State createState(Workspace workspace) {
        State state = new State(1L, "state", false);
        state.changeWorkspace(workspace);
        return state;
    }

    private static WorkspaceMember createWorkspaceMember(Member member, Workspace workspace) {
        return new WorkspaceMember(member, workspace, "");
    }

    private static MemberRole createMemberRole(Member member, Role role) {
        return new MemberRole(member, role);
    }
}*/
