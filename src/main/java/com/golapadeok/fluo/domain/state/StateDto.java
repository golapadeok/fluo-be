package com.golapadeok.fluo.domain.state;

import lombok.Getter;

@Getter
public class StateDto {
    private final String stateId;
    private final String workspaceId;
    private final String name;

    public StateDto(String stateId, String workspaceId, String name) {
        this.stateId = stateId;
        this.workspaceId = workspaceId;
        this.name = name;
    }
}
