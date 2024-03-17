package com.golapadeok.fluo.domain.task.exception;

import lombok.Getter;

@Getter
public enum TaskStatus {
    NOT_FOUND_TASK(400, "존재하지 않는 업무입니다.");

    private final int status;
    private final String message;

    TaskStatus(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
