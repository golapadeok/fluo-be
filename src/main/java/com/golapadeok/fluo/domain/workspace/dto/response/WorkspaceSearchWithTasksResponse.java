package com.golapadeok.fluo.domain.workspace.dto.response;

import com.golapadeok.fluo.domain.task.domain.Task;
import com.golapadeok.fluo.domain.task.dto.TaskDto;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

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
}
