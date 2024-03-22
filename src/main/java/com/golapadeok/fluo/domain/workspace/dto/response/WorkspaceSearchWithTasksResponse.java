package com.golapadeok.fluo.domain.workspace.dto.response;

import com.golapadeok.fluo.domain.task.domain.Task;
import com.golapadeok.fluo.domain.task.dto.TaskDto;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import com.golapadeok.fluo.domain.workspace.dto.CustomPageImpl;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

@Getter
public class WorkspaceSearchWithTasksResponse {
    private final Integer total;
    private final Integer limit;
    private final Integer cursorId;
    private final List<TaskDto> tasks;

    public WorkspaceSearchWithTasksResponse(Integer total, Integer limit, Integer cursorId, List<TaskDto> tasks) {
        this.total = total;
        this.limit = limit;
        this.cursorId = cursorId;
        this.tasks = tasks;
    }

    public static WorkspaceSearchWithTasksResponse of(CustomPageImpl<Task> tasks) {
        return new WorkspaceSearchWithTasksResponse(
                (int) tasks.getTotalElements(),
                tasks.getSize(),
                (int) tasks.getNextCursor(),
                TaskDto.of(tasks.getContent())
        );
    }
}
