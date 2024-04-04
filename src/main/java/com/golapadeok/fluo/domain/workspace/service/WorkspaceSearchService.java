package com.golapadeok.fluo.domain.workspace.service;

import com.golapadeok.fluo.domain.invitation.dto.InvitationCodeDto;
import com.golapadeok.fluo.domain.member.domain.WorkspaceMember;
import com.golapadeok.fluo.domain.role.domain.MemberRole;
import com.golapadeok.fluo.domain.tag.domain.Tag;
import com.golapadeok.fluo.domain.tag.dto.TagDto;
import com.golapadeok.fluo.domain.task.domain.Task;
import com.golapadeok.fluo.domain.task.dto.TaskDto;
import com.golapadeok.fluo.domain.workspace.dto.MemberWithRoleDto;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import com.golapadeok.fluo.domain.workspace.dto.request.CursorPageRequest;
import com.golapadeok.fluo.domain.workspace.dto.request.FilterRequest;
import com.golapadeok.fluo.domain.workspace.dto.response.*;
import com.golapadeok.fluo.domain.workspace.exception.NotFoundWorkspaceException;
import com.golapadeok.fluo.domain.workspace.repository.WorkspaceRepository;
import com.golapadeok.fluo.domain.workspace.repository.WorkspaceRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

    public WorkspaceSearchResponse search(long workspaceId) {
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(NotFoundWorkspaceException::new);

        return new WorkspaceSearchResponse(workspace);
    }

    public WorkspaceSearchWithTasksResponse searchWithTasks(long workspaceId, CursorPageRequest request, FilterRequest filterRequest) {
        Page<Task> pageTasks = workspaceRepositoryImpl.searchPageTasks2(workspaceId, request, filterRequest);
        return WorkspaceSearchWithTasksResponse.of(pageTasks.getTotalPages(), pageTasks.getSize(), pageTasks.getNumber(), TaskDto.of(pageTasks.getContent()));
    }

    public WorkspaceSearchWithStatesResponse searchWithStates(long workspaceId) {
        return workspaceRepositoryImpl.findWorkspaceWithStates(workspaceId);
    }

    public WorkspaceSearchWithMembersResponse searchWithMembers(long workspaceId) {
        //조회할 워크스페이스
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(NotFoundWorkspaceException::new);

        List<MemberWithRoleDto> members = workspace.getWorkspaceMembers().stream()
                .map(WorkspaceMember::getMember)
                .map(member -> new MemberWithRoleDto(member,
                        member.getMemberRoles().stream()
                                .map(MemberRole::getRole)
                                .filter(role -> role.getWorkspace().getId().equals(workspaceId))
                                .toList()))
                .toList();

        return new WorkspaceSearchWithMembersResponse(workspace.getTitle(),members);
    }

    public WorkspaceSearchWithTagsResponse searchWithTags(long workspaceId) {
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(NotFoundWorkspaceException::new);

        List<Tag> tags = workspace.getTags();
        return new WorkspaceSearchWithTagsResponse(TagDto.of(tags));
//        return workspaceRepositoryImpl.findWorkspaceWithTags(workspaceId);
    }

    public InvitationCodeDto searchWithInvitationCode(long workspaceId) {
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(NotFoundWorkspaceException::new);

        String invitationCode = workspace.getInvitationCode();
        return new InvitationCodeDto(invitationCode);
    }
}
