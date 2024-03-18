package com.golapadeok.fluo.domain.task.dto.response;

import com.golapadeok.fluo.domain.task.domain.ScheduleRange;
import com.golapadeok.fluo.domain.task.domain.Task;
import com.golapadeok.fluo.domain.task.domain.TaskConfiguration;
import com.golapadeok.fluo.domain.state.dto.StateDto;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Getter
public class TaskSearchResponse {
    private final String taskId;
    private final String title;
    private final String description;
    private final String creator;
    private final StateDto state;
    private final List<String> managers;
    private final Boolean isPrivate;
    private final Integer priority;
    private final LocalDate startDate;
    private final LocalDate endDate;

    private TaskSearchResponse(String taskId, String title, String description, TaskConfiguration configuration, StateDto state, ScheduleRange scheduleRange) {
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.creator = configuration.getCreator();
        this.managers = Arrays.asList(configuration.getManager().split(","));
        this.isPrivate = configuration.getIsPrivate();
        this.priority = configuration.getPriority();
        this.state = state;
        this.startDate = scheduleRange.getStartDate().toLocalDate();
        this.endDate = scheduleRange.getEndDate().toLocalDate();
    }

    public static TaskSearchResponse of(Task task) {
        return new TaskSearchResponse(
                task.getId().toString(),
                task.getTitle(),
                task.getDescription(),
                task.getConfiguration(),
                StateDto.of(task.getState()),
                task.getScheduleRange());
    }
}
