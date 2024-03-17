package com.golapadeok.fluo.domain.task.dto.response;

import com.golapadeok.fluo.domain.task.domain.ScheduleRange;
import com.golapadeok.fluo.domain.task.domain.Task;
import com.golapadeok.fluo.domain.task.domain.TaskConfiguration;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Getter
public class TaskSearchResponse {
    private String taskId;
    private String title;
    private String description;
    private String creator;
    private List<String> managers;
    private Boolean isPrivate;
    private Integer priority;
    private LocalDate startDate;
    private LocalDate endDate;

    private TaskSearchResponse(String taskId, String title, String description, TaskConfiguration configuration, ScheduleRange scheduleRange) {
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.creator = configuration.getCreator();
        this.managers = Arrays.asList(configuration.getManager().split(","));
        this.isPrivate = configuration.getIsPrivate();
        this.priority = configuration.getPriority();
        this.startDate = scheduleRange.getStartDate().toLocalDate();
        this.endDate = scheduleRange.getEndDate().toLocalDate();
    }

    public static TaskSearchResponse of(Task task) {
        return new TaskSearchResponse(
                task.getId().toString(),
                task.getTitle(),
                task.getDescription(),
                task.getConfiguration(),
                task.getScheduleRange()
        );
    }
}
