package com.golapadeok.fluo.domain.task.service;

import com.golapadeok.fluo.domain.member.domain.Member;
import com.golapadeok.fluo.domain.member.repository.MemberRepository;
import com.golapadeok.fluo.domain.state.domain.State;
import com.golapadeok.fluo.domain.state.exception.NotFoundStateException;
import com.golapadeok.fluo.domain.state.repository.StateRepository;
import com.golapadeok.fluo.domain.tag.domain.Tag;
import com.golapadeok.fluo.domain.tag.exception.NotFoundTagException;
import com.golapadeok.fluo.domain.tag.repository.TagRepository;
import com.golapadeok.fluo.domain.task.domain.ScheduleRange;
import com.golapadeok.fluo.domain.task.domain.Task;
import com.golapadeok.fluo.domain.task.domain.TaskConfiguration;
import com.golapadeok.fluo.domain.task.dto.request.TaskCreateRequest;
import com.golapadeok.fluo.domain.task.dto.response.TaskCreateResponse;
import com.golapadeok.fluo.domain.task.repository.TaskRepository;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import com.golapadeok.fluo.domain.workspace.exception.NotFoundWorkspaceException;
import com.golapadeok.fluo.domain.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskCreateService {
    private final TagRepository tagRepository;
    private final MemberRepository memberRepository;
    private final WorkspaceRepository workspaceRepository;
    private final StateRepository stateRepository;
    private final TaskRepository taskRepository;

    @Transactional
    public TaskCreateResponse createTask(TaskCreateRequest request) {
        Workspace workspace = findWorkspaceById(request.getWorkspaceId());
        State state = findStateByIdAndWorkspaceId(request.getStateId(), request.getWorkspaceId());

        List<Tag> tags = tagRepository.findByIdInAndWorkspaceId(request.getTags(), request.getWorkspaceId());
        List<Member> members = memberRepository.findByIdIn(request.getManagers());

        Task task = newTask(request, joiningManagerId(members), joiningTagId(tags));
        task.changeWorkspace(workspace);
        task.changeState(state);
        task = taskRepository.save(task);

        return TaskCreateResponse.of(task, members, tags);
    }



    private Task newTask(TaskCreateRequest request, String memberId, String tagId) {
        return Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .creator(request.getCreator())
                .manager(memberId)
                .tag(tagId)
                .configuration(extractTaskConfigure(request))
                .scheduleRange(extractScheduleRange(request))
                .build();
    }

    private State findStateByIdAndWorkspaceId(long stateId, long workspaceId) {
        return stateRepository.findByIdAndWorkspaceId(stateId, workspaceId)
                .orElseThrow(NotFoundStateException::new);
    }

    private Workspace findWorkspaceById(long workspaceId) {
        return workspaceRepository.findById(workspaceId)
                .orElseThrow(NotFoundWorkspaceException::new);
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

    private String joiningManagerId(List<Member> members) {
        // TODO KDY 회원 적용하면 주석 제거
//        if (members.isEmpty())
//            throw new IllegalArgumentException("관리자가 존재하지 않습니다.");

        return members.stream()
                .map(member -> member.getId().toString())
                .collect(Collectors.joining(","));
    }

    private String joiningTagId(List<Tag> tags) {
        if (tags.isEmpty())
            throw new NotFoundTagException();

        return tags.stream()
                .map(tag -> tag.getId().toString())
                .collect(Collectors.joining(","));
    }
}
