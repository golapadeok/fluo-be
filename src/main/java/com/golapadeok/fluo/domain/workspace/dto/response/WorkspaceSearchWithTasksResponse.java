package com.golapadeok.fluo.domain.workspace.dto.response;

import com.golapadeok.fluo.domain.state.domain.State;
import com.golapadeok.fluo.domain.task.domain.ScheduleRange;
import com.golapadeok.fluo.domain.task.domain.Task;
import com.golapadeok.fluo.domain.task.domain.TaskConfiguration;
import com.golapadeok.fluo.domain.task.dto.StateDto;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import lombok.Getter;

import java.time.LocalDate;
import java.util.*;

@Getter
public class WorkspaceSearchWithTasksResponse {
    private final String workspaceId;
    private final String title;
    private final String description;
    private final LocalDate createDate;
    private final List<TaskDto> tasks;

    private WorkspaceSearchWithTasksResponse(String workspaceId, String title, String description, LocalDate createDate, List<TaskDto> tasks) {
        this.workspaceId = workspaceId;
        this.title = title;
        this.description = description;
        this.createDate = createDate;
        this.tasks = tasks;
    }

    public static WorkspaceSearchWithTasksResponse of(Workspace workspace, List<Task> tasks) {
        return new WorkspaceSearchWithTasksResponse(
                workspace.getId().toString(),
                workspace.getTitle(),
                workspace.getDescription(),
                workspace.getCreateDate().toLocalDate(),
                TaskDto.of(tasks)
        );
    }

    @Getter
    private static class TaskDto {
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

        private static TaskDto of(Task task) {
            StateDto convertState = StateDto.of(task.getState());
            return new TaskDto(task.getId().toString(), task.getTitle(), task.getDescription(), task.getConfiguration(), convertState, task.getScheduleRange());
        }

        public static List<TaskDto> of(List<Task> tasks) {
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
}
