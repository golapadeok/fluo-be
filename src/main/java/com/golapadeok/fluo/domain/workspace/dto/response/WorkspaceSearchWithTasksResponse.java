package com.golapadeok.fluo.domain.workspace.dto.response;

import com.golapadeok.fluo.domain.task.dto.TaskDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class WorkspaceSearchWithTasksResponse {
    private final Integer total;
    private final Integer limit;
    private final Integer cursorId;
    private final List<TaskDto> tasks;

    @Builder
    private WorkspaceSearchWithTasksResponse(Integer total, Integer limit, Integer cursorId, List<TaskDto> tasks) {
        this.total = total;
        this.limit = limit;
        this.cursorId = cursorId;
        this.tasks = tasks;
    }

    public static WorkspaceSearchWithTasksResponse of(int total, int limit, int cursorId, List<TaskDto> tasks) {
        return WorkspaceSearchWithTasksResponse.builder()
                .total(total)
                .limit(limit)
                .cursorId(cursorId)
                .tasks(tasks)
                .build();
    }
}
