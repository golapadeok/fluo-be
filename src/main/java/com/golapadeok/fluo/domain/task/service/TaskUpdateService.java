package com.golapadeok.fluo.domain.task.service;

import com.golapadeok.fluo.domain.member.domain.Member;
import com.golapadeok.fluo.domain.member.repository.MemberRepository;
import com.golapadeok.fluo.domain.state.domain.State;
import com.golapadeok.fluo.domain.state.exception.NotFoundStateException;
import com.golapadeok.fluo.domain.state.repository.StateRepository;
import com.golapadeok.fluo.domain.tag.domain.Tag;
import com.golapadeok.fluo.domain.tag.exception.NotFoundTagException;
import com.golapadeok.fluo.domain.tag.repository.TagRepository;
import com.golapadeok.fluo.domain.task.domain.ManagerTask;
import com.golapadeok.fluo.domain.task.domain.ScheduleRange;
import com.golapadeok.fluo.domain.task.domain.Task;
import com.golapadeok.fluo.domain.task.domain.TaskConfiguration;
import com.golapadeok.fluo.domain.task.dto.request.TaskUpdateRequest;
import com.golapadeok.fluo.domain.task.dto.response.TaskDetailResponse;
import com.golapadeok.fluo.domain.task.exception.NotFoundTaskException;
import com.golapadeok.fluo.domain.task.repository.ManagerTaskRepository;
import com.golapadeok.fluo.domain.task.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TaskUpdateService {
    private final TagRepository tagRepository;
    private final MemberRepository memberRepository;
    private final TaskRepository taskRepository;
    private final StateRepository stateRepository;
    private final ManagerTaskRepository managerTaskRepository;

    @Transactional
    public TaskDetailResponse update(long taskId, TaskUpdateRequest request) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(NotFoundTaskException::new);

        //Manager 수정
//        updateManagers(task, request.getManagers());

        //Tag 수정
        Tag tag = tagRepository.findById(request.getTag().longValue())
                .orElseThrow(NotFoundTagException::new);
        task.changeTag(tag);

        //State 수정
        State state = stateRepository.findById(request.getStateId().longValue())
                .orElseThrow(NotFoundStateException::new);
        task.changeState(state);

        //Task 내용 수정
        ScheduleRange scheduleRange = extractScheduleRange(request);
        TaskConfiguration configuration = extractTaskConfigure(request);
        task.changeTask(task.toBuilder()
                .title(request.getTitle())
                .description(request.getDescription())
                .creator(request.getCreator())
                .configuration(configuration)
                .scheduleRange(scheduleRange)
                .build());

        taskRepository.flush();
        return new TaskDetailResponse(task);
    }


    /**
     * Update Task Managers
     */
    private void updateManagers(Task task, List<Integer> managerIds) {
        List<Member> members = memberRepository.findByIdIn(managerIds);
        List<ManagerTask> managers = task.getManagers();


        //새로운 매니저 등록
        List<ManagerTask> saves = members.stream()
                .filter(member -> managers.stream().map(managerTask -> managerTask.getMember().getId()).noneMatch(managerId -> Objects.equals(managerId, member.getId())))
                .map(member -> {
                    ManagerTask managerTask = new ManagerTask();
                    managerTask.changeTask(task);
                    managerTask.changeMember(member);
                    return managerTask;
                }).toList();
        managerTaskRepository.saveAll(saves);

        //이전 매니저 삭제
        List<ManagerTask> deletes = managers.stream()
                .filter(managerTask -> members.stream().noneMatch(member -> Objects.equals(member.getId(), managerTask.getMember().getId())))
                .map(manager -> {
                    manager.getTask().getManagers().remove(manager);
                    manager.changeTask(null);
                    return manager;
                }).toList();
        managerTaskRepository.deleteAll(deletes);
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
