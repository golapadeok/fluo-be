package com.golapadeok.fluo.domain.task.service;

import com.golapadeok.fluo.domain.state.domain.State;
import com.golapadeok.fluo.domain.state.repository.StateRepository;
import com.golapadeok.fluo.domain.task.domain.ScheduleRange;
import com.golapadeok.fluo.domain.task.domain.Task;
import com.golapadeok.fluo.domain.task.domain.TaskConfiguration;
import com.golapadeok.fluo.domain.task.dto.request.TaskCreateRequest;
import com.golapadeok.fluo.domain.task.dto.response.TaskCreateResponse;
import com.golapadeok.fluo.domain.task.repository.TaskRepository;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import com.golapadeok.fluo.domain.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaskCreateService {
    private final WorkspaceRepository workspaceRepository;
    private final StateRepository stateRepository;
    private final TaskRepository taskRepository;

    @Transactional
    public TaskCreateResponse createTask(TaskCreateRequest request) {

        Workspace workspace = findWorkspaceById(request.getWorkspaceId());
        State state = findStateById(request.getStateId());

        Task task = newTask(request);
        task.changeWorkspace(workspace);
        task.changeState(state);
        task = taskRepository.save(task);

        return TaskCreateResponse.of(task);
    }

    private Task newTask(TaskCreateRequest request) {
        return Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .creator(request.getCreator())
                .manager(String.join(",", request.getManagers()))
                .configuration(extractTaskConfigure(request))
                .scheduleRange(extractScheduleRange(request))
                .build();
    }

    private State findStateById(long stateId) {
        return stateRepository.findById(stateId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상태입니다."));
    }

    private Workspace findWorkspaceById(long workspaceId) {
        return workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 워크스페이스입니다."));
    }

    private ScheduleRange extractScheduleRange(TaskCreateRequest request) {
        return new ScheduleRange(
                request.getStartDate(),
                request.getEndDate()
        );
    }

    private TaskConfiguration extractTaskConfigure(TaskCreateRequest request) {
        return new TaskConfiguration(
                request.getIsPrivate(),
                request.getPriority()
        );
    }
}
