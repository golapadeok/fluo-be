package com.golapadeok.fluo.domain.task.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TaskDeleteResponse {
    private final String message;

    @Builder
    public TaskDeleteResponse(String message) {
        this.message = message;
    }
}
