package com.golapadeok.fluo.domain.task.service;

import com.golapadeok.fluo.domain.member.domain.Member;
import com.golapadeok.fluo.domain.member.repository.MemberRepository;
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

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskUpdateService {
    private final MemberRepository memberRepository;
    private final TaskRepository taskRepository;
    private final StateRepository stateRepository;

    @Transactional
    public TaskUpdateResponse update(Integer taskId, TaskUpdateRequest request) {
        Task task = findTaskById(taskId);

        List<Member> members = memberRepository.findByIdIn(request.getManagers());
        Task updateTask = updateTask(task, members, request);

        task.changeTask(updateTask);
        task.changeState(findStateById(request.getStateId(), task.getWorkspace().getId()));

        taskRepository.flush();
        return TaskUpdateResponse.of(task, members);
    }

    private Task updateTask(Task task, List<Member> members, TaskUpdateRequest request) {

        return task.toBuilder()
                .title(request.getTitle())
                .description(request.getDescription())
                .creator(request.getCreator())
                .manager(joiningManagerId(members))
                .configuration(extractTaskConfigure(request))
                .scheduleRange(extractScheduleRange(request))
                .build();
    }

    private State findStateById(long stateId, long workspaceId) {
        return stateRepository.findByIdAndWorkspaceId(stateId, workspaceId)
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

    private String joiningManagerId(List<Member> members) {
        // TODO KDY 회원 적용하면 주석 제거
//        if (members.isEmpty())
//            throw new IllegalArgumentException("관리자가 존재하지 않습니다.");

        return members.stream()
                .map(member -> member.getId().toString())
                .collect(Collectors.joining(","));
    }
}
