package com.golapadeok.fluo.domain.task.dto.response;

import com.golapadeok.fluo.domain.task.domain.Task;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class TaskResponse {
    private final String taskId;
    private final String title;
    private final String description;
    private final String creator;
    private final Boolean isPrivate;
    private final Integer priority;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public TaskResponse(Task task) {
        this.taskId = task.getId().toString();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.creator = task.getCreator();
        this.isPrivate = task.getConfiguration().getIsPrivate();
        this.priority = task.getConfiguration().getPriority();
        this.startDate = task.getScheduleRange().getStartDate().toLocalDate();
        this.endDate = task.getScheduleRange().getEndDate().toLocalDate();
    }
}
