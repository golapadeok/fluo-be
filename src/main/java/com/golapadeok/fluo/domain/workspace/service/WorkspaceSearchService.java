package com.golapadeok.fluo.domain.workspace.service;

import com.golapadeok.fluo.domain.task.domain.Task;
import com.golapadeok.fluo.domain.task.dto.TaskDto;
import com.golapadeok.fluo.domain.task.dto.response.TaskSearchResponse;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import com.golapadeok.fluo.domain.workspace.dto.request.WorkspacePageRequest;
import com.golapadeok.fluo.domain.workspace.dto.response.*;
import com.golapadeok.fluo.domain.workspace.exception.NotFoundWorkspaceException;
import com.golapadeok.fluo.domain.workspace.repository.WorkspaceRepository;
import com.golapadeok.fluo.domain.workspace.repository.WorkspaceRepositoryImpl;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.hibernate.jdbc.Work;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorkspaceSearchService {
    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceRepositoryImpl workspaceRepositoryImpl;

    public List<WorkspacePageResponse> searches(WorkspacePageRequest request) {
        PageRequest pageRequest = PageRequest.of(request.getOffset(), request.getLimit());
        Page<Workspace> pages = workspaceRepository.findAll(pageRequest);
        List<Workspace> contents = pages.getContent();
        return WorkspacePageResponse.of(contents);
    }

    public WorkspaceSearchResponse search(Integer workspaceId) {
        Workspace workspace = getWorkspace(workspaceId);
        return WorkspaceSearchResponse.of(workspace);
    }

    public WorkspaceSearchWithTasksResponse searchWithTasks(Integer workspaceId, WorkspacePageRequest pageRequest) {
        Workspace workspace = getWorkspace(workspaceId);

        Page<Task> tasks = workspaceRepositoryImpl.searchPage(workspaceId, pageRequest.getLimit(), pageRequest.getOffset(), pageRequest.getAscending());
        List<Task> content = tasks.getContent();
        
        return WorkspaceSearchWithTasksResponse.of(workspace, content);
    }

    public WorkspaceSearchWithStatesResponse searchWithStates(Integer workspaceId) {
        Workspace workspace = getWorkspace(workspaceId);
        return WorkspaceSearchWithStatesResponse.of(workspace);
    }

    public WorkspaceSearchWithMembersResponse searchWithMembers(Integer workspaceId) {
        Workspace workspace = getWorkspace(workspaceId);
        return WorkspaceSearchWithMembersResponse.of(workspace);
    }

    private Workspace getWorkspace(int workspaceId) {
        return workspaceRepository.findById((long) workspaceId)
                .orElseThrow(NotFoundWorkspaceException::new);
    }
}
