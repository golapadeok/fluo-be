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

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorkspaceSearchService {
    private final TagRepository tagRepository;
    private final MemberRepository memberRepository;
    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceRepositoryImpl workspaceRepositoryImpl;

    public BaseResponse searches() {
        List<Workspace> workspaces = workspaceRepository.findAll();
        List<WorkspacePageResponse> response = WorkspacePageResponse.of(workspaces);
        return new BaseResponse(response);
    }

    public WorkspaceSearchResponse search(Integer workspaceId) {
        Workspace workspace = getWorkspace(workspaceId);

        CursorPageRequest cursorPageRequest = new CursorPageRequest(100, 0, true);
        FilterRequest filterRequest = new FilterRequest(null, null, null, null, null, null);

        WorkspaceSearchWithTasksResponse tasksResponse = searchWithTasks(workspaceId, cursorPageRequest, filterRequest);
        WorkspaceSearchWithStatesResponse statesResponse = searchWithStates(workspaceId);
        WorkspaceSearchWithMembersResponse membersResponse = searchWithMembers(workspaceId);
        WorkspaceSearchWithTagsResponse tagResponse = searchWithTags(workspaceId);

        return WorkspaceSearchResponse.builder()
                .workspaceId(workspace.getId().toString())
                .title(workspace.getTitle())
                .description(workspace.getDescription())
                .imageUrl(workspace.getImageUrl())
                .states(statesResponse.getStates())
                .tasks(tasksResponse.getTasks())
                .members(membersResponse.getMembers())
                .tags(tagResponse.getTags())
                .createDate(workspace.getCreateDate().toLocalDate())
                .build();
    }


    //TODO KDY 수정
    public WorkspaceSearchWithTasksResponse searchWithTasks(Integer workspaceId, CursorPageRequest pageRequest, FilterRequest filterRequest) {
        CustomPageImpl<Task> pageTasks = workspaceRepositoryImpl.searchPageTasks(workspaceId, pageRequest, filterRequest);
        List<Task> tasks = pageTasks.getContent();
        List<TaskDto> results = new ArrayList<>();
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
