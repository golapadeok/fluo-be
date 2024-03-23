package com.golapadeok.fluo.domain.task.service;

import com.golapadeok.fluo.domain.state.domain.State;
import com.golapadeok.fluo.domain.state.repository.StateRepository;
import com.golapadeok.fluo.domain.task.domain.ScheduleRange;
import com.golapadeok.fluo.domain.task.domain.Task;
import com.golapadeok.fluo.domain.task.domain.TaskConfiguration;
import com.golapadeok.fluo.domain.task.dto.request.TaskCreateRequest;
import com.golapadeok.fluo.domain.task.dto.response.TaskCreateResponse;
import com.golapadeok.fluo.domain.task.repository.TaskRepository;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import com.golapadeok.fluo.domain.workspace.repository.WorkspaceRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TaskCreateServiceTest {
    @InjectMocks
    private TaskCreateService taskCreateService;

    @Mock
    private TaskRepository taskRepository;
    @Mock
    private WorkspaceRepository workspaceRepository;
    @Mock
    private StateRepository stateRepository;

    @Test
    @DisplayName("업무 생성 성공 테스트")
    void createTask() {
        //given
        TaskCreateRequest request = new TaskCreateRequest(
                1, 0, "test", "description", "creator1", List.of("manager1", "manager2"), true, 1, LocalDate.now(), LocalDate.now()
        );

        TaskConfiguration taskConfiguration = new TaskConfiguration(true, 1);
        ScheduleRange scheduleRange = new ScheduleRange(LocalDate.now(), LocalDate.now());
        Task task = new Task(1L, "title", "description", "creator1", "manager1,manager2", taskConfiguration, scheduleRange);

        //when
        given(workspaceRepository.findById(request.getWorkspaceId().longValue()))
                .willReturn(Optional.of(new Workspace("workspace1", "workspace", "url")));

        given(stateRepository.findById(request.getStateId().longValue()))
                .willReturn(Optional.of(new State(1L, "state")));

        given(taskRepository.save(any(Task.class)))
                .willReturn(task);

        //then
        TaskCreateResponse response = taskCreateService.createTask(request);
        Assertions.assertThat(response.getTaskId()).isEqualTo("1");
        Assertions.assertThat(response.getCreator()).isEqualTo("creator1");
        Assertions.assertThat(response.getManagers()).hasSize(2);
    }
}