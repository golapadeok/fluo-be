package com.golapadeok.fluo.domain.workspace.dto.response;

import com.golapadeok.fluo.domain.state.dto.StateDto;
import com.golapadeok.fluo.domain.tag.dto.TagDto;
import com.golapadeok.fluo.domain.task.dto.TaskDto;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import com.golapadeok.fluo.domain.workspace.dto.MemberDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class WorkspaceSearchResponse {
    private final String workspaceId;
    private final String title;
    private final String description;
    private final String imageUrl;
    private final List<StateDto> states;
    private final List<TaskDto> tasks;
    private final List<MemberDto> members;
    private final List<TagDto> tags;
    private final LocalDate createDate;

    @Builder
    public WorkspaceSearchResponse(String workspaceId, String title, String description, String imageUrl, List<StateDto> states, List<TaskDto> tasks, List<MemberDto> members, List<TagDto> tags, LocalDate createDate) {
        this.workspaceId = workspaceId;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.states = states;
        this.tasks = tasks;
        this.members = members;
        this.tags = tags;
        this.createDate = createDate;
    }

    public static WorkspaceSearchResponse of(Workspace workspace, List<TaskDto> tasks, List<StateDto> states, List<MemberDto> members, List<TagDto> tags) {
        return WorkspaceSearchResponse.builder()
                .workspaceId(workspace.getId().toString())
                .title(workspace.getTitle())
                .description(workspace.getDescription())
                .imageUrl(workspace.getImageUrl())
                .states(states)
                .tasks(tasks)
                .members(members)
                .tags(tags)
                .createDate(workspace.getCreateDate().toLocalDate())
                .build();
    }
}
