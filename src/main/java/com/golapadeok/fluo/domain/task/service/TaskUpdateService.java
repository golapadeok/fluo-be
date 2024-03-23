package com.golapadeok.fluo.domain.task.service;

import com.golapadeok.fluo.domain.state.domain.State;
import com.golapadeok.fluo.domain.state.exception.NotFoundStateException;
import com.golapadeok.fluo.domain.state.repository.StateRepository;
import com.golapadeok.fluo.domain.task.domain.ScheduleRange;
import com.golapadeok.fluo.domain.task.domain.Task;
import com.golapadeok.fluo.domain.task.domain.TaskConfiguration;
import com.golapadeok.fluo.domain.task.dto.request.TaskUpdateRequest;
import com.golapadeok.fluo.domain.task.dto.response.TaskUpdateResponse;
import com.golapadeok.fluo.domain.task.exception.NotFoundTaskException;
import com.golapadeok.fluo.domain.task.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaskUpdateService {
    private final TaskRepository taskRepository;
    private final StateRepository stateRepository;

    @Transactional
    public TaskUpdateResponse update(Integer taskId, TaskUpdateRequest request) {
        Task task = findTaskById(taskId);
        Task updateTask = updateTask(task, request);

        task.changeTask(updateTask);
        task.changeState(findStateById(request.getStateId()));

        taskRepository.flush();
        return TaskUpdateResponse.of(task);
    }

    private Task updateTask(Task task, TaskUpdateRequest request) {
        return task.toBuilder()
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
                .orElseThrow(NotFoundStateException::new);
    }

    private Task findTaskById(long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(NotFoundTaskException::new);
    }

    private ScheduleRange extractScheduleRange(TaskUpdateRequest request) {
        return new ScheduleRange(
                request.getStartDate(),
                request.getEndDate()
        );
    }

    private TaskConfiguration extractTaskConfigure(TaskUpdateRequest request) {
        return new TaskConfiguration(
                request.getIsPrivate(),
                request.getPriority()
        );
    }
}
