package com.golapadeok.fluo.domain.workspace.service;

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

@Service
@RequiredArgsConstructor
public class WorkspaceDeleteService {
    private final WorkspaceRepository workspaceRepository;
    private final TagRepository tagRepository;
    private final StateRepository stateRepository;
    private final TaskRepository taskRepository;

    @Transactional
    public WorkspaceDeleteResponse delete(Integer workspaceId) {
        Workspace workspace = findByWorkspaceId(workspaceId);

        //State 삭제
        deleteStates(workspace.getStates());
        workspace.getStates().removeAll(workspace.getStates());

        //Task 삭제
        taskRepository.deleteAll(workspace.getTasks());
        workspace.getTasks().removeAll(workspace.getTasks());

        //Tag 삭제
        tagRepository.deleteAll(workspace.getTags());
        workspace.getTags().removeAll(workspace.getTags());

        workspaceRepository.deleteById(workspaceId.longValue());
        return WorkspaceDeleteResponse.of("워크스페이스 삭제에 성공했습니다.");
    }

    private void deleteStates(List<State> states) {
        stateRepository.deleteAll(states);
    }

    private Workspace findByWorkspaceId(long workspaceId) {
        return workspaceRepository.findById(workspaceId)
                .orElseThrow(NotFoundWorkspaceException::new);
    }
}
