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
import com.golapadeok.fluo.domain.workspace.util.InvitationCodeGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkspaceCreateService {
    private final RoleRepository roleRepository;
    private final MemberRoleRepository memberRoleRepository;
    private final WorkspaceMemberRepository workspaceMemberRepository;
    private final WorkspaceRepository workspaceRepository;
    private final StateRepository stateRepository;
    private static final String DEFAULT_IMAGE = "https://fluo-bucket.s3.ap-northeast-2.amazonaws.com/images/default_ea81d464-2433-4ca2-9281-691187752a05_S88da226f2926405ba7abe131b6e55a90w.png";

    private static final List<String> DEFAULT_CREDENTIAL = List.of(
            "DELETE_WORKSPACE",
            "INVITE_MEMBER",
            "REMOVE_MEMBER",
            "CREATE_ROLE",
            "MODIFY_ROLE",
            "DELETE_ROLE",
            "ASSIGN_ROLE",
            "CREATE_TASK",
            "MODIFY_TASK",
            "DELETE_TASK"
    );

    @Transactional
    public WorkspaceResponse create(PrincipalDetails principal, WorkspaceCreateRequest request) {
        if (principal == null || principal.getMember() == null) {
            throw new IllegalArgumentException("로그인해야 합니다.");
        }

        String title = request.getTitle();
        String description = request.getDescription();
        String invitationCode = extractedInvitationCode();

        //Workspace
        Workspace workspace = new Workspace(title, description, DEFAULT_IMAGE, principal.getMember().getName());
        workspace.changeInvitationCode(invitationCode);
        workspaceRepository.save(workspace);

        //State
        State state = new State("default", true);
        state.changeWorkspace(workspace);
        stateRepository.save(state);


        WorkspaceMember workspaceMember = new WorkspaceMember(principal.getMember(), workspace, "");
        workspaceMemberRepository.save(workspaceMember);

        String credential = String.join(",", DEFAULT_CREDENTIAL);
        Role defaultRole = new Role("default", credential, workspace);
        roleRepository.save(defaultRole);

        Member member = principal.getMember();
        memberRoleRepository.save(new MemberRole(member, defaultRole));

        return new WorkspaceResponse(workspace);
    }

    private String extractedInvitationCode() {
        String generator = InvitationCodeGenerator.generator();
        while (workspaceRepository.existsByInvitationCode(generator)) {
            generator = InvitationCodeGenerator.generator();
        }
        return generator;
    }
}
