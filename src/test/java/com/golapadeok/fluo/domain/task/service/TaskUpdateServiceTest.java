/*
package com.golapadeok.fluo.domain.task.service;

import com.golapadeok.fluo.domain.member.domain.Member;
import com.golapadeok.fluo.domain.member.repository.MemberRepository;
import com.golapadeok.fluo.domain.state.domain.State;
import com.golapadeok.fluo.domain.state.repository.StateRepository;
import com.golapadeok.fluo.domain.tag.domain.Tag;
import com.golapadeok.fluo.domain.tag.repository.TagRepository;
import com.golapadeok.fluo.domain.task.domain.ManagerTask;
import com.golapadeok.fluo.domain.task.domain.ScheduleRange;
import com.golapadeok.fluo.domain.task.domain.Task;
import com.golapadeok.fluo.domain.task.domain.TaskConfiguration;
import com.golapadeok.fluo.domain.task.dto.request.TaskUpdateRequest;
import com.golapadeok.fluo.domain.task.dto.response.TaskDetailResponse;
import com.golapadeok.fluo.domain.task.repository.ManagerTaskRepository;
import com.golapadeok.fluo.domain.task.repository.TaskRepository;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@Transactional
@ExtendWith(MockitoExtension.class)
class TaskUpdateServiceTest {
    @InjectMocks
    private TaskUpdateService taskUpdateService;

    @Mock
    private TagRepository tagRepository;
    @Mock
    private MemberRepository memberRepository;
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private StateRepository stateRepository;
    @Mock
    private ManagerTaskRepository managerTaskRepository;

    @Test
    @DisplayName("업무 수정 성공 케이스")
    void update() {
        //given

        //Member
        Member member1 = new Member(1L, "email1", "name1", "profile1", null, null, null);
        Member member2 = new Member(2L, "email2", "name2", "profile2", null, null, null);

        List<Member> members = List.of(member1, member2);
        List<Member> members2 = List.of(member1);

        //Workspace
        Workspace workspace = new Workspace(1L, "title", "description", "imageUrl");

        //State
        State state = new State(1L, "state", true);
        state.changeWorkspace(workspace);

        State state2 = new State(2L, "state2", false);
        state.changeWorkspace(workspace);

        //Tag
        Tag tag = new Tag(1L, "tagName", "######");
        tag.changeWorkspace(workspace);

        Tag tag2 = new Tag(2L, "tagName2", "#####2");
        tag.changeWorkspace(workspace);

        //Task
        TaskConfiguration taskConfiguration = new TaskConfiguration(true, 1);
        ScheduleRange scheduleRange = new ScheduleRange(LocalDate.now(), LocalDate.now());
        Task task = new Task(1L, "title", "description", "creator1", taskConfiguration, scheduleRange);
        task.changeWorkspace(workspace);
        task.changeState(state);
        task.changeTag(tag);

        TaskConfiguration taskConfiguration2 = new TaskConfiguration(false, 10);
        ScheduleRange scheduleRange2 = new ScheduleRange(LocalDate.now(), LocalDate.now());

        Task task2 = new Task(1L, "updateTitle", "updateDescription", "updateCreator", taskConfiguration2, scheduleRange2);
        task.changeWorkspace(workspace);
        task.changeState(state);
        task.changeTag(tag2);

        //ManagerTask
        ManagerTask managerTask1 = new ManagerTask();
        managerTask1.changeTask(task);
        managerTask1.changeMember(member1);

        ManagerTask managerTask2 = new ManagerTask();
        managerTask2.changeTask(task);
        managerTask2.changeMember(member2);


        TaskUpdateRequest request = new TaskUpdateRequest(
                2, "updateTitle", "updateDescription",
                "updateCreator", 2, List.of(1), false, 10, LocalDate.now(), LocalDate.now()
        );

        given(taskRepository.findById(1L)).willReturn(Optional.of(task));
        given(memberRepository.findByIdIn(request.getManagers())).willReturn(members2);
        given(tagRepository.findById(request.getTag().longValue())).willReturn(Optional.of(tag2));
        given(stateRepository.findById(request.getStateId().longValue())).willReturn(Optional.of(state2));
//        verify(managerTaskRepository).saveAll(task);
//        verify(managerTaskRepository).deleteAll(any());

        //when
        TaskDetailResponse response = taskUpdateService.update(1L, request);


        //then
        assertThat(response).isNotNull();
        assertThat(response.getTaskId()).isEqualTo("1");
        assertThat(response.getTitle()).isEqualTo(task2.getTitle());
        assertThat(response.getDescription()).isEqualTo(task2.getDescription());
        assertThat(response.getCreator()).isEqualTo(task2.getCreator());
        assertThat(response.getIsPrivate()).isFalse();
        assertThat(response.getPriority()).isEqualTo(task2.getConfiguration().getPriority());

        assertThat(response.getManagers()).hasSize(1);
        assertThat(response.getManagers().get(0).getId()).isEqualTo("1");
    }
}*/
