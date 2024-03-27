package com.golapadeok.fluo.domain.state.dto.response;

import com.golapadeok.fluo.domain.state.domain.State;
import lombok.Getter;

@Getter
public class StateUpdateResponse extends StateResponse {
    public StateUpdateResponse(State state) {
        super(state);
    }
}
