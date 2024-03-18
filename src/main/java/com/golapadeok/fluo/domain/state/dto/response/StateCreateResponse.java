package com.golapadeok.fluo.domain.state.dto.response;

import com.golapadeok.fluo.domain.state.domain.State;
import lombok.Getter;

@Getter
public class StateCreateResponse {
    private final String stateId;
    private final String name;

    private StateCreateResponse(String stateId, String name) {
        this.stateId = stateId;
        this.name = name;
    }

    public static StateCreateResponse of(State state) {
        return new StateCreateResponse(
                state.getId().toString(),
                state.getName()
        );
    }
}
