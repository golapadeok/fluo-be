package com.golapadeok.fluo.domain.workspace.service;

import com.golapadeok.fluo.domain.member.repository.MemberRepository;
import com.golapadeok.fluo.domain.tag.repository.TagRepository;
import com.golapadeok.fluo.domain.task.domain.Task;
import com.golapadeok.fluo.domain.task.dto.TaskDto;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import com.golapadeok.fluo.domain.workspace.dto.CustomPageImpl;
import com.golapadeok.fluo.domain.workspace.dto.request.CursorPageRequest;
import com.golapadeok.fluo.domain.workspace.dto.request.FilterRequest;
import com.golapadeok.fluo.domain.workspace.dto.response.*;
import com.golapadeok.fluo.domain.workspace.exception.NotFoundWorkspaceException;
import com.golapadeok.fluo.domain.workspace.repository.WorkspaceRepository;
import com.golapadeok.fluo.domain.workspace.repository.WorkspaceRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorkspaceSearchService {
    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceRepositoryImpl workspaceRepositoryImpl;

    public BaseResponse searches() {
        List<Workspace> workspaces = workspaceRepository.findAll();
        List<WorkspacePageResponse> response = WorkspacePageResponse.of(workspaces);
        return new BaseResponse(response);
    }

    public WorkspaceSearchResponse search(Integer workspaceId) {
        Workspace workspace = getWorkspace(workspaceId);
        return new WorkspaceSearchResponse(workspace);
    }


    public WorkspaceSearchWithTasksResponse searchWithTasks(Integer workspaceId, CursorPageRequest pageRequest, FilterRequest filterRequest) {
        CustomPageImpl<Task> pageTasks = workspaceRepositoryImpl.searchPageTasks(workspaceId, pageRequest, filterRequest);
        List<Task> tasks = pageTasks.getContent();
        List<TaskDto> results = TaskDto.of(tasks);
        return WorkspaceSearchWithTasksResponse.of((int) pageTasks.getTotalElements(), pageTasks.getSize(), (int) pageTasks.getNextCursor(), results);
    }

    public WorkspaceSearchWithStatesResponse searchWithStates(Integer workspaceId) {
        return workspaceRepositoryImpl.findWorkspaceWithStates(workspaceId);
    }

    public WorkspaceSearchWithMembersResponse searchWithMembers(Integer workspaceId) {
        return workspaceRepositoryImpl.findWorkspaceWithMembers(workspaceId);
    }

    public WorkspaceSearchWithTagsResponse searchWithTags(Integer workspaceId) {
        return workspaceRepositoryImpl.findWorkspaceWithTags(workspaceId);
    }

    private Workspace getWorkspace(int workspaceId) {
        return workspaceRepository.findById((long) workspaceId)
                .orElseThrow(NotFoundWorkspaceException::new);
    }


}
