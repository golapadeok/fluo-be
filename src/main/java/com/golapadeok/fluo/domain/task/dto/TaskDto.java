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
    private final List<MemberDto> managers;
    private final StateDto state;
    private final Boolean isPrivate;
    private final Integer priority;
    private final LocalDate startDate;
    private final LocalDate endDate;

    private TaskDto(String taskId, String title, String description, String creator, List<MemberDto> managers, TaskConfiguration configuration, StateDto state, ScheduleRange scheduleRange) {
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.creator = creator;
        this.managers = managers;
        this.isPrivate = configuration.getIsPrivate();
        this.priority = configuration.getPriority();
        this.state = state;
        this.startDate = scheduleRange.getStartDate().toLocalDate();
        this.endDate = scheduleRange.getEndDate().toLocalDate();
    }

    public static TaskDto of(Task task, List<MemberDto> members) {
        Assert.notNull(task, "task must not be null");
        StateDto convertState = StateDto.of(task.getState());
        return new TaskDto(task.getId().toString(), task.getTitle(), task.getDescription(), task.getCreator(), members, task.getConfiguration(), convertState, task.getScheduleRange());
    }

    public static List<TaskDto> of(List<Task> tasks) {
        Assert.notNull(tasks, "tasks must not be null");
        Iterator<Task> iterator = tasks.iterator();
        List<TaskDto> results = new ArrayList<>();
        while (iterator.hasNext()) {
            Task task = iterator.next();
            TaskDto taskDto = of(task, null);
            results.add(taskDto);
        }

        return Collections.unmodifiableList(results);
    }
}
