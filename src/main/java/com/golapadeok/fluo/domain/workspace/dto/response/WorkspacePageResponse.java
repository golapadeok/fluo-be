package com.golapadeok.fluo.domain.workspace.dto.response;

import com.golapadeok.fluo.domain.state.domain.State;
import com.golapadeok.fluo.domain.state.dto.StateDto;
import com.golapadeok.fluo.domain.task.domain.Task;
import com.golapadeok.fluo.domain.task.dto.TaskDto;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Getter
//TODO KDY MEMBER 추가 필요
public class WorkspacePageResponse {
    private final String workspaceId;
    private final String title;
    private final String description;
    private final String imageUrl;
    private final List<StateDto> state;
    private final List<TaskDto> taskDto;
    private final LocalDate createDate;

    private WorkspacePageResponse(String workspaceId, String title, String description, String imageUrl, List<StateDto> state, List<TaskDto> taskDto, LocalDate createDate) {
        this.workspaceId = workspaceId;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.state = state;
        this.taskDto = taskDto;
        this.createDate = createDate;
    }

    public static WorkspacePageResponse of(Workspace workspace) {
        List<State> states = workspace.getStates();
        List<Task> tasks = workspace.getTasks();

        return new WorkspacePageResponse(
                workspace.getId().toString(),
                workspace.getTitle(),
                workspace.getDescription(),
                workspace.getImageUrl(),
                StateDto.of(states),
                TaskDto.of(tasks),
                workspace.getCreateDate().toLocalDate()
        );
    }

    public static List<WorkspacePageResponse> of(List<Workspace> workspaces) {
        Iterator<Workspace> iterator = workspaces.iterator();
        List<WorkspacePageResponse> results = new ArrayList<>();
        while (iterator.hasNext()) {
            Workspace workspace = iterator.next();
            WorkspacePageResponse response = of(workspace);
            results.add(response);
        }

        return Collections.unmodifiableList(results);
    }
}
