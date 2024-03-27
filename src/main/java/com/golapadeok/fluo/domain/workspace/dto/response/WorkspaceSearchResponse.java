package com.golapadeok.fluo.domain.workspace.dto.response;

import com.golapadeok.fluo.domain.member.domain.WorkspaceMember;
import com.golapadeok.fluo.domain.state.dto.StateDto;
import com.golapadeok.fluo.domain.tag.dto.TagDto;
import com.golapadeok.fluo.domain.task.dto.TaskDto;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import com.golapadeok.fluo.domain.workspace.dto.MemberDto;
import lombok.Getter;

import java.util.List;

@Getter
public class WorkspaceSearchResponse extends WorkspaceResponse {
    private final List<StateDto> states;
    private final List<TaskDto> tasks;
    private final List<MemberDto> members;
    private final List<TagDto> tags;

    public WorkspaceSearchResponse(Workspace workspace) {
        super(workspace);
        this.states = StateDto.of(workspace.getStates());
        this.tasks = TaskDto.of(workspace.getTasks());
        this.members = MemberDto.of(workspace.getWorkspaceMembers().stream().map(WorkspaceMember::getMember).toList());
        this.tags = TagDto.of(workspace.getTags());
    }
}
