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
import com.golapadeok.fluo.domain.task.dto.request.TaskCreateRequest;
import com.golapadeok.fluo.domain.task.dto.response.TaskDetailResponse;
import com.golapadeok.fluo.domain.task.repository.ManagerTaskRepository;
import com.golapadeok.fluo.domain.task.repository.TaskRepository;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import com.golapadeok.fluo.domain.workspace.exception.NotFoundWorkspaceException;
import com.golapadeok.fluo.domain.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskCreateService {
    private final TagRepository tagRepository;
    private final MemberRepository memberRepository;
    private final WorkspaceRepository workspaceRepository;
    private final StateRepository stateRepository;
    private final TaskRepository taskRepository;
    private final ManagerTaskRepository managerTaskRepository;


    @Transactional
    public TaskDetailResponse createTask(TaskCreateRequest request) {
        Workspace workspace = workspaceRepository.findById(request.getWorkspaceId().longValue())
                .orElseThrow(NotFoundWorkspaceException::new);

        State state = stateRepository.findByIdAndWorkspaceId(request.getStateId(), request.getWorkspaceId())
                .orElseThrow(NotFoundStateException::new);

        List<Member> members = memberRepository.findByIdIn(request.getManagers());

        Tag tag = tagRepository.findByIdInAndWorkspaceId(request.getTag(), request.getWorkspaceId())
                .orElseGet(() -> null);

        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .creator(request.getCreator())
                .configuration(extractTaskConfigure(request))
                .scheduleRange(extractScheduleRange(request))
                .build();

        task.changeWorkspace(workspace);
        task.changeState(state);
        task.changeTag(tag);
        taskRepository.save(task);

        for (Member member : members) {
            ManagerTask managerTask = new ManagerTask();
            managerTask.changeTask(task);
            managerTask.changeMember(member);
            managerTaskRepository.save(managerTask);
        }


        return new TaskDetailResponse(task);
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
