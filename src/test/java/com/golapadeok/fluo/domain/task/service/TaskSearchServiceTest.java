package com.golapadeok.fluo.domain.task.service;

import com.golapadeok.fluo.domain.member.domain.Member;
import com.golapadeok.fluo.domain.state.domain.State;
import com.golapadeok.fluo.domain.tag.domain.Tag;
import com.golapadeok.fluo.domain.task.domain.*;
import com.golapadeok.fluo.domain.task.dto.response.TaskDetailResponse;
import com.golapadeok.fluo.domain.task.exception.NotFoundTaskException;
import com.golapadeok.fluo.domain.task.repository.TaskRepository;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@Transactional
@ExtendWith(MockitoExtension.class)
class TaskSearchServiceTest {

    @InjectMocks
    private TaskSearchService taskSearchService;

    @Mock
    private TaskRepository taskRepository;

    @Test
    @DisplayName("업무 단일조회 성공 케이스")
    void search() {
        //given

        //Member
        List<Member> members = List.of(
                new Member(1L, "email1", "name1", "profile1", null, null, null),
                new Member(2L, "email2", "name2", "profile2", null, null, null)
        );

        //Workspace
        Workspace workspace = new Workspace(1L, "title", "description", "imageUrl");

        //State
        State state = new State(1L, "state", true);
        state.changeWorkspace(workspace);

        //Tag
        Tag tag = new Tag(1L, "tagName", "######");
        tag.changeWorkspace(workspace);

        //Task
        TaskConfiguration taskConfiguration = new TaskConfiguration(true, 1);
        ScheduleRange scheduleRange = new ScheduleRange(LocalDate.now(), LocalDate.now());
        Task task = new Task(1L, "title", "description", "creator1", LabelColor.RED, taskConfiguration, scheduleRange);
        task.changeWorkspace(workspace);
        task.changeState(state);
        task.changeTag(tag);

        //ManagerTask
        ManagerTask managerTask1 = new ManagerTask();
        managerTask1.changeTask(task);
        managerTask1.changeMember(members.get(0));

        ManagerTask managerTask2 = new ManagerTask();
        managerTask2.changeTask(task);
        managerTask2.changeMember(members.get(1));

        given(taskRepository.findById(1L)).willReturn(Optional.of(task));

        //when
        TaskDetailResponse response = taskSearchService.search(1L);

        //then
        assertThat(response).isNotNull();
        assertThat(response.getTaskId()).isEqualTo("1");
        assertThat(response.getTitle()).isEqualTo("title");
        assertThat(response.getDescription()).isEqualTo("description");
        assertThat(response.getCreator()).isEqualTo("creator1");
        assertThat(response.getIsPrivate()).isTrue();
        assertThat(response.getPriority()).isEqualTo(1);

        assertThat(response.getManagers()).hasSize(2);
        assertThat(response.getManagers().get(1).getId()).isEqualTo("2");
        assertThat(response.getManagers().get(1).getName()).isEqualTo("name2");
    }
}