package com.golapadeok.fluo.domain.task.service;

import com.golapadeok.fluo.domain.task.domain.ScheduleRange;
import com.golapadeok.fluo.domain.task.domain.Task;
import com.golapadeok.fluo.domain.task.domain.TaskConfiguration;
import com.golapadeok.fluo.domain.task.dto.request.TaskUpdateRequest;
import com.golapadeok.fluo.domain.task.dto.response.TaskUpdateResponse;
import com.golapadeok.fluo.domain.task.exception.NotFoundTaskException;
import com.golapadeok.fluo.domain.task.repository.TaskRepository;
import com.golapadeok.fluo.domain.workspace.domain.State;
import com.golapadeok.fluo.domain.workspace.repository.StateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class TaskUpdateService {
    private final TaskRepository taskRepository;
    private final StateRepository stateRepository;

    @Transactional
    public TaskUpdateResponse update(Integer taskId, TaskUpdateRequest request) {
        final long id = taskId.longValue();
        Task task = taskRepository.findById(id)
                .orElseThrow(NotFoundTaskException::new);

        final TaskConfiguration configuration = getTasConfiguration(request);
        final ScheduleRange scheduleRange = getScheduleRange(request);
        final State state = getState(request.getStateId());

        task.changeState(state);
        task.changeTaskConfiguration(configuration);
        task.changeScheduleRange(scheduleRange);
        task.changeTitle(request.getTitle());
        task.changeDescription(request.getDescription());

        taskRepository.flush();
        return TaskUpdateResponse.of(task);
    }

    private State getState(long stateId) {
        return stateRepository.findById(stateId)
                .orElseGet(() -> null);
//         TODO 에러 클래스 생성하면 추가하기 KDY
//                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상태입니다."));
    }

    private ScheduleRange getScheduleRange(TaskUpdateRequest request) {
        LocalDate startDate = request.getStartDate();
        LocalDate endDate = request.getEndDate();
        return new ScheduleRange(startDate, endDate);
    }

    private TaskConfiguration getTasConfiguration(TaskUpdateRequest request) {
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
