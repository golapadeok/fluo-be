package com.golapadeok.fluo.domain.task.dto;

import com.golapadeok.fluo.domain.workspace.domain.State;
import lombok.Getter;

@Getter
public class StateDto {
    private final String stateId;
    private final String name;

    private StateDto(String stateId, String name) {
        this.stateId = stateId;
        this.name = name;
    }

    public static StateDto of(State state) {
        return new StateDto(state.getId().toString(), state.getName());
    }
}
