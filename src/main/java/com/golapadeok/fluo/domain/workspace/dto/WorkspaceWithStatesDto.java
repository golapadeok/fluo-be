package com.golapadeok.fluo.domain.workspace.dto;

import com.golapadeok.fluo.domain.state.StateDto;
import lombok.Getter;

import java.util.List;

@Getter
public class WorkspaceWithStatesDto {
    private final String workspaceId;
    private final String title;
    private final String description;
    private final List<StateDto> stateDto;

    public WorkspaceWithStatesDto(String workspaceId, String title, String description, List<StateDto> stateDto) {
        this.workspaceId = workspaceId;
        this.title = title;
        this.description = description;
        this.stateDto = stateDto;
    }
}
