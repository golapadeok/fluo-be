package com.golapadeok.fluo.domain.task.dto;

import com.golapadeok.fluo.domain.state.dto.StateDto;
import com.golapadeok.fluo.domain.task.domain.ScheduleRange;
import com.golapadeok.fluo.domain.task.domain.Task;
import com.golapadeok.fluo.domain.task.domain.TaskConfiguration;
import lombok.Getter;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.*;

@Getter
public class TaskDto {
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

    private TaskDto(String taskId, String title, String description, TaskConfiguration configuration, StateDto state, ScheduleRange scheduleRange) {
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

    public static TaskDto of(Task task) {
        Assert.notNull(task, "task must not be null");
        StateDto convertState = StateDto.of(task.getState());
        return new TaskDto(task.getId().toString(), task.getTitle(), task.getDescription(), task.getConfiguration(), convertState, task.getScheduleRange());
    }

    public static List<TaskDto> of(List<Task> tasks) {
        Assert.notNull(tasks, "tasks must not be null");
        Iterator<Task> iterator = tasks.iterator();
        List<TaskDto> results = new ArrayList<>();
        while (iterator.hasNext()) {
            Task task = iterator.next();
            TaskDto taskDto = of(task);
            results.add(taskDto);
        }

        return Collections.unmodifiableList(results);
    }
}
