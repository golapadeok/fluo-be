package com.golapadeok.fluo.domain.state.exception;

import lombok.Getter;

@Getter
public enum StateStatus {
    NOT_FOUND_STATE(404, "존재하지 않는 상태 입니다.");

    private final int status;
    private final String message;

    StateStatus(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
