package com.golapadeok.fluo.domain.task.dto.response;

import lombok.Getter;

@Getter
public class TaskDeleteResponse {
    private final String message;

    private TaskDeleteResponse(String message) {
        this.message = message;
    }

    public static TaskDeleteResponse of(String message) {
        return new TaskDeleteResponse(message);
    }
}
