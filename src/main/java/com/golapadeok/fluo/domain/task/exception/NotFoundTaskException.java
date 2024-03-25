package com.golapadeok.fluo.domain.task.exception;

import lombok.Getter;

@Getter
public class NotFoundTaskException extends RuntimeException {
    private final TaskStatus taskStatus;

    public NotFoundTaskException() {
        this.taskStatus = TaskStatus.NOT_FOUND_TASK;
    }
}
