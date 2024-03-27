package com.golapadeok.fluo.domain.state.dto;

import com.golapadeok.fluo.domain.state.domain.State;
import lombok.Getter;
import org.springframework.util.Assert;

import java.util.List;

@Getter
public class StateDto {
    private final String stateId;
    private final String name;


    public StateDto(Long stateId, String name) {
        this.stateId = stateId.toString();
        this.name = name;
    }
    public StateDto(String stateId, String name) {
        this.stateId = stateId;
        this.name = name;
    }

    public static StateDto of(State state) {
        Assert.notNull(state, "state must not be null");
        return new StateDto(state.getId().toString(), state.getName());
    }

    public static List<StateDto> of(List<State> states) {
        Assert.notNull(states, "states must not be null");
        return states.stream()
                .map(StateDto::of)
                .toList();
    }
}
