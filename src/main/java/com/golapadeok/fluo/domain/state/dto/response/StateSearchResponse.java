package com.golapadeok.fluo.domain.state.dto.response;

import com.golapadeok.fluo.domain.state.domain.State;
import lombok.Getter;

@Getter
public class StateSearchResponse extends StateResponse {
    public StateSearchResponse(State state) {
        super(state);
    }
}
