package com.golapadeok.fluo.domain.workspace.dto.response;

import com.golapadeok.fluo.domain.state.dto.StateDto;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class WorkspaceSearchWithStatesResponse {
    private final String workspaceId;
    private final String title;
    private final String description;
    private final LocalDate createDate;
    private final List<StateDto> states;

    private WorkspaceSearchWithStatesResponse(String workspaceId, String title, String description, LocalDate createDate, List<StateDto> states) {
        this.workspaceId = workspaceId;
        this.title = title;
        this.description = description;
        this.createDate = createDate;
        this.states = states;
    }

    public static WorkspaceSearchWithStatesResponse of(Workspace workspace) {
        return new WorkspaceSearchWithStatesResponse(
                workspace.getId().toString(),
                workspace.getTitle(),
                workspace.getDescription(),
                workspace.getCreateDate().toLocalDate(),
                StateDto.of(workspace.getStates())
        );
    }
}
