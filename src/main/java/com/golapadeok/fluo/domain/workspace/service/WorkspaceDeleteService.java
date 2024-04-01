package com.golapadeok.fluo.domain.workspace.service;

import com.golapadeok.fluo.domain.member.domain.Member;
import com.golapadeok.fluo.domain.member.domain.WorkspaceMember;
import com.golapadeok.fluo.domain.member.repository.WorkspaceMemberRepository;
import com.golapadeok.fluo.domain.role.domain.MemberRole;
import com.golapadeok.fluo.domain.role.domain.Role;
import com.golapadeok.fluo.domain.state.domain.State;
import com.golapadeok.fluo.domain.state.repository.StateRepository;
import com.golapadeok.fluo.domain.tag.domain.Tag;
import com.golapadeok.fluo.domain.tag.repository.TagRepository;
import com.golapadeok.fluo.domain.task.domain.Task;
import com.golapadeok.fluo.domain.task.repository.TaskRepository;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import com.golapadeok.fluo.domain.workspace.dto.response.WorkspaceDeleteResponse;
import com.golapadeok.fluo.domain.workspace.exception.NotFoundWorkspaceException;
import com.golapadeok.fluo.domain.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class WorkspaceDeleteService {
    private final WorkspaceMemberRepository workspaceMemberRepository;
    private final WorkspaceRepository workspaceRepository;
    private final TagRepository tagRepository;
    private final StateRepository stateRepository;
    private final TaskRepository taskRepository;

    @Transactional
    public WorkspaceDeleteResponse delete(long workspaceId) {
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(NotFoundWorkspaceException::new);

        //State 삭제
        deleteStates(workspace.getStates());
        workspace.getStates().removeAll(workspace.getStates());

        //Task 삭제
        taskRepository.deleteAll(workspace.getTasks());
        workspace.getTasks().removeAll(workspace.getTasks());

        //Tag 삭제
        tagRepository.deleteAll(workspace.getTags());
        workspace.getTags().removeAll(workspace.getTags());

        workspaceRepository.deleteById(workspaceId);
        return WorkspaceDeleteResponse.of("워크스페이스 삭제에 성공했습니다.");
    }

    @Transactional
    public WorkspaceDeleteResponse deleteMember(long workspaceId, long memberId) {
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(NotFoundWorkspaceException::new);

        WorkspaceMember workspaceMember1 = workspace.getWorkspaceMembers()
                .stream().filter(workspaceMember -> workspaceMember.getMember().getId().equals(memberId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        String name = workspaceMember1.getMember().getName();
        if (workspace.getCreator().equals(name))
            throw new IllegalArgumentException("워크스페이스 제작자는 추방할 수 없습니다.");

        workspace.getWorkspaceMembers().remove(workspaceMember1);
        workspaceMemberRepository.delete(workspaceMember1);
        return WorkspaceDeleteResponse.of("회원 삭제에 성공했습니다.");
    }

    private void deleteStates(List<State> states) {
        stateRepository.deleteAll(states);
    }
}
