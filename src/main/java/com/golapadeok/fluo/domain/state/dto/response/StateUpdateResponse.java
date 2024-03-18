package com.golapadeok.fluo.domain.state.dto.response;

import com.golapadeok.fluo.domain.state.domain.State;
import lombok.Getter;

@Getter
public class StateUpdateResponse {
    private final String stateId;
    private final String name;

    private StateUpdateResponse(String stateId, String name) {
        this.stateId = stateId;
        this.name = name;
    }

    public static StateUpdateResponse of(State state) {
        return new StateUpdateResponse(state.getId().toString(), state.getName());
    }
}
