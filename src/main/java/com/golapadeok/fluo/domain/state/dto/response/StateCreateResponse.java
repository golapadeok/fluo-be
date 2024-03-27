package com.golapadeok.fluo.domain.state.dto.response;

import com.golapadeok.fluo.domain.state.domain.State;
import lombok.Getter;

@Getter
public class StateCreateResponse extends StateResponse {
    public StateCreateResponse(State state) {
        super(state);
    }
}
