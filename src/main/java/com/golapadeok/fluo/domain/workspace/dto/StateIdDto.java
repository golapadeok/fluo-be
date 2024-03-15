package com.golapadeok.fluo.domain.workspace.dto;

import lombok.Getter;

@Getter
public class StateIdDto {
    private final String stateId;

    public StateIdDto(String stateId) {
        this.stateId = stateId;
    }
}
