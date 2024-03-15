package com.golapadeok.fluo.domain.task.service;

import com.golapadeok.fluo.domain.task.domain.Task;
import com.golapadeok.fluo.domain.task.dto.TaskDto;
import com.golapadeok.fluo.domain.task.dto.TaskIdDto;
import com.golapadeok.fluo.domain.task.dto.request.TaskRequest;
import com.golapadeok.fluo.domain.task.repository.TaskRepository;
import com.golapadeok.fluo.domain.task.util.TaskMapper;
import com.golapadeok.fluo.domain.workspace.domain.State;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import com.golapadeok.fluo.domain.workspace.repository.StateRepository;
import com.golapadeok.fluo.domain.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final WorkspaceRepository workspaceRepository;
    private final TaskRepository taskRepository;
    private final StateRepository stateRepository;
    private final TaskMapper taskMapper;

    public TaskDto getTask(Integer taskId) {
        Task task = taskRepository.findById(taskId.longValue())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 업무입니다."));
        return taskMapper.convertTaskToDto(task);
    }

    @Transactional
    public TaskDto createTask(TaskRequest request) {
        Workspace workspace = workspaceRepository.findById(request.getWorkspaceId().longValue())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 워크스페이스입니다."));

        State state = stateRepository.findById(request.getStateId().longValue())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상태입니다."));

        Task task = taskMapper.convertTaskRequestToEntity(request);
        task.changeState(state);
        return taskMapper.convertTaskToDto(task);
    }

    @Transactional
    public TaskDto updateTask(Integer taskId, TaskRequest request) {
        Task task = taskRepository.findById(taskId.longValue())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 업무입니다."));
        task.updateTask(request);

        if (task.getState().getId() != request.getStateId().longValue()) {
            State state = stateRepository.findById(request.getStateId().longValue())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상태입니다."));
            task.changeState(state);
        }

        return taskMapper.convertTaskToDto(task);
    }

    public TaskIdDto deleteTask(Integer taskId) {
        taskRepository.deleteById(taskId.longValue());
        return new TaskIdDto(taskId.toString());
    }

}
