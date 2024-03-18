package com.golapadeok.fluo.domain.workspace.service;

import com.golapadeok.fluo.domain.state.domain.State;
import com.golapadeok.fluo.domain.task.domain.Task;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import com.golapadeok.fluo.domain.workspace.dto.response.WorkspaceSearchResponse;
import com.golapadeok.fluo.domain.workspace.dto.response.WorkspaceSearchWithStatesResponse;
import com.golapadeok.fluo.domain.workspace.dto.response.WorkspaceSearchWithTasksResponse;
import com.golapadeok.fluo.domain.workspace.exception.NotFoundWorkspaceException;
import com.golapadeok.fluo.domain.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorkspaceSearchService {
    private final WorkspaceRepository workspaceRepository;

    public WorkspaceSearchResponse search(Integer workspaceId) {
        Workspace workspace = getWorkspace(workspaceId);
        return WorkspaceSearchResponse.of(workspace);
    }

    public WorkspaceSearchWithTasksResponse searchWithTasks(Integer workspaceId) {
        Workspace workspace = getWorkspace(workspaceId);
        List<Task> tasks = workspace.getTasks();
        return WorkspaceSearchWithTasksResponse.of(workspace, tasks);
    }

    public WorkspaceSearchWithStatesResponse searchWithStates(Integer workspaceId) {
        Workspace workspace = getWorkspace(workspaceId);
        return WorkspaceSearchWithStatesResponse.of(workspace);

    }

    private Workspace getWorkspace(int workspaceId) {
        return workspaceRepository.findById((long) workspaceId)
                .orElseThrow(NotFoundWorkspaceException::new);
    }
}
