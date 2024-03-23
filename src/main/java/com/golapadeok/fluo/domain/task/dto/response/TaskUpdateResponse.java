package com.golapadeok.fluo.domain.task.dto.response;

import com.golapadeok.fluo.domain.task.domain.ScheduleRange;
import com.golapadeok.fluo.domain.task.domain.Task;
import com.golapadeok.fluo.domain.task.domain.TaskConfiguration;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Getter
public class TaskUpdateResponse {
    private final String taskId;
    private final String title;
    private final String description;
    private final String creator;
    private final List<String> managers;
    private final Boolean isPrivate;
    private final Integer priority;
    private final LocalDate startDate;
    private final LocalDate endDate;

    private TaskUpdateResponse(String taskId, String title, String description, String creator, String manager, TaskConfiguration configuration, ScheduleRange scheduleRange) {
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.creator = creator;
        this.managers = Arrays.asList(manager.split(","));
        this.isPrivate = configuration.getIsPrivate();
        this.priority = configuration.getPriority();
        this.startDate = scheduleRange.getStartDate().toLocalDate();
        this.endDate = scheduleRange.getEndDate().toLocalDate();
    }

    public static TaskUpdateResponse of(Task task) {
        return new TaskUpdateResponse(
                task.getId().toString(),
                task.getTitle(),
                task.getDescription(),
                task.getCreator(),
                task.getManager(),
                task.getConfiguration(),
                task.getScheduleRange()
        );
    }
}
