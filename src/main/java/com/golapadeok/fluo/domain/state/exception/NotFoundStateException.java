package com.golapadeok.fluo.domain.state.exception;

import lombok.Getter;

@Getter
public class NotFoundStateException extends RuntimeException {
    private final StateStatus stateStatus;

    public NotFoundStateException() {
        this.stateStatus = StateStatus.NOT_FOUND_STATE;
    }
}
