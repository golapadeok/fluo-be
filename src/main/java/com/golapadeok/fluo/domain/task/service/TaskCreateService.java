package com.golapadeok.fluo.domain.task.service;

import com.golapadeok.fluo.domain.task.domain.ScheduleRange;
import com.golapadeok.fluo.domain.task.domain.Task;
import com.golapadeok.fluo.domain.task.domain.TaskConfiguration;
import com.golapadeok.fluo.domain.task.dto.request.TaskCreateRequest;
import com.golapadeok.fluo.domain.task.dto.response.TaskCreateResponse;
import com.golapadeok.fluo.domain.task.repository.TaskRepository;
import com.golapadeok.fluo.domain.task.util.TaskMapper;
import com.golapadeok.fluo.domain.state.State;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import com.golapadeok.fluo.domain.state.StateRepository;
import com.golapadeok.fluo.domain.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class TaskCreateService {
    private final WorkspaceRepository workspaceRepository;
    private final StateRepository stateRepository;
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;

    @Transactional
    public TaskCreateResponse createTask(TaskCreateRequest request) {
        final long workspaceId = request.getWorkspaceId().longValue();
        final long stateId = request.getStateId().longValue();

        final String title = request.getTitle();
        final String description = request.getDescription();
        final ScheduleRange scheduleRange = getScheduleRange(request);
        final TaskConfiguration tasConfiguration = getTasConfiguration(request);

        Workspace workspace = getWorkspace(workspaceId);
        State state = getState(stateId);

        Task task = new Task(title, description, tasConfiguration, scheduleRange);
        task.changeWorkspace(workspace);
        task.changeState(state);
        taskRepository.save(task);

        return taskMapper.convertResponseToEntity(task);
    }

    private State getState(long stateId) {
        return stateRepository.findById(stateId)
                .orElseGet(() -> null);
//         TODO 에러 클래스 생성하면 추가하기 KDY
//                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상태입니다."));
    }

    private Workspace getWorkspace(long workspaceId) {
        return workspaceRepository.findById(workspaceId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 워크스페이스입니다."));
    }

    private ScheduleRange getScheduleRange(TaskCreateRequest request) {
        LocalDate startDate = request.getStartDate();
        LocalDate endDate = request.getEndDate();
        return new ScheduleRange(startDate, endDate);
    }

    private TaskConfiguration getTasConfiguration(TaskCreateRequest request) {
        String creator = request.getCreator();
        String managers = String.join(",", request.getManagers());
        return new TaskConfiguration(
                creator,
                managers,
                request.getIsPrivate(),
                request.getPriority()
        );
    }
}
