package com.golapadeok.fluo.domain.task.dto;

import lombok.Getter;

@Getter
public class TaskIdDto {
    private final String taskId;

    public TaskIdDto(String taskId) {
        this.taskId = taskId;
    }
}
