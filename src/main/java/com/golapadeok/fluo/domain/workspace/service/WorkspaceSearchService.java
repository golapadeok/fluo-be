package com.golapadeok.fluo.domain.workspace.service;

import com.golapadeok.fluo.domain.task.domain.Task;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import com.golapadeok.fluo.domain.workspace.dto.response.WorkspaceCreateResponse;
import com.golapadeok.fluo.domain.workspace.dto.response.WorkspaceSearchResponse;
import com.golapadeok.fluo.domain.workspace.dto.response.WorkspaceWithTaskSearchResponse;
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
        Workspace workspace = workspaceRepository.findById(workspaceId.longValue())
                .orElseThrow(NotFoundWorkspaceException::new);

        return WorkspaceSearchResponse.of(workspace);
    }

    public WorkspaceWithTaskSearchResponse searchWithTasks(Integer workspaceId) {
        Workspace workspace = workspaceRepository.findById(workspaceId.longValue())
                .orElseThrow(NotFoundWorkspaceException::new);

        List<Task> tasks = workspace.getTasks();
        return WorkspaceWithTaskSearchResponse.of(workspace, tasks);
    }
}
