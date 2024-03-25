package com.golapadeok.fluo.domain.task;

import lombok.Getter;

@Getter
public class StateIdDto {
    private final String stateId;

    public StateIdDto(String stateId) {
        this.stateId = stateId;
    }
}
