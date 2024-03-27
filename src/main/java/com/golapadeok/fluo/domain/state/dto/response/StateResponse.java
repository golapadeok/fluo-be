package com.golapadeok.fluo.domain.state.dto.response;

import com.golapadeok.fluo.domain.state.domain.State;
import lombok.Getter;

@Getter
public class StateResponse {
    private final String stateId;
    private final String name;

    public StateResponse(State state) {
        this.stateId = state.getId().toString();
        this.name = state.getName();
    }
}
