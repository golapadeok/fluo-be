package com.golapadeok.fluo.domain.workspace.dto.response;

import com.golapadeok.fluo.domain.state.dto.StateDto;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class WorkspaceSearchWithStatesResponse {
    private final List<StateDto> states;

    public WorkspaceSearchWithStatesResponse(List<StateDto> states) {
        this.states = states;
    }
}
