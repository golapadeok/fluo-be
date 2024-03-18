package com.golapadeok.fluo.domain.state.dto.response;

import com.golapadeok.fluo.domain.state.domain.State;
import lombok.Getter;

@Getter
public class StateSearchResponse {
    private final String stateId;
    private final String name;

    private StateSearchResponse(String stateId, String name) {
        this.stateId = stateId;
        this.name = name;
    }

    public static StateSearchResponse of(State state) {
        return new StateSearchResponse(state.getId().toString(), state.getName());
    }
}
