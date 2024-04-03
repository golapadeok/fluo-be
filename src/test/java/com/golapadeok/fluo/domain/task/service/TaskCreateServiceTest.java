package com.golapadeok.fluo.domain.task.service;

import com.golapadeok.fluo.domain.member.domain.Member;
import com.golapadeok.fluo.domain.member.repository.MemberRepository;
import com.golapadeok.fluo.domain.state.domain.State;
import com.golapadeok.fluo.domain.state.repository.StateRepository;
import com.golapadeok.fluo.domain.tag.domain.Tag;
import com.golapadeok.fluo.domain.tag.repository.TagRepository;
import com.golapadeok.fluo.domain.task.domain.*;
import com.golapadeok.fluo.domain.task.dto.request.TaskCreateRequest;
import com.golapadeok.fluo.domain.task.dto.response.TaskDetailResponse;
import com.golapadeok.fluo.domain.task.repository.ManagerTaskRepository;
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
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;

@Transactional
@ExtendWith(MockitoExtension.class)
class TaskCreateServiceTest {
    @InjectMocks
    private TaskCreateService taskCreateService;
    @InjectMocks
    private TaskUpdateService taskUpdateService;

    @Mock
    private MemberRepository memberRepository;
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private WorkspaceRepository workspaceRepository;
    @Mock
    private StateRepository stateRepository;
    @Mock
    private TagRepository tagRepository;

    @Mock
    private ManagerTaskRepository managerTaskRepository;

    @Test
    @DisplayName("업무 생성 성공 케이스")
    void createTask() {
        //given
        List<Member> members = List.of(
                new Member(1L, "email1", "name1", "profile1", null, null, null),
                new Member(2L, "email2", "name2", "profile2", null, null, null)
        );

        Workspace workspace = new Workspace(1L, "title", "description", "imageUrl", "member");

        State state = new State(1L, "state", true);
        state.changeWorkspace(workspace);

        Tag tag = new Tag(1L, "tagName", "######");
        tag.changeWorkspace(workspace);

        TaskConfiguration taskConfiguration = new TaskConfiguration(true, 1);
        ScheduleRange scheduleRange = new ScheduleRange(LocalDate.now(), LocalDate.now());
        Task task = new Task(1L, "title", "description", 1, LabelColor.RED, taskConfiguration, scheduleRange);
        task.changeWorkspace(workspace);
        task.changeState(state);
        task.changeTag(tag);

        TaskCreateRequest request = new TaskCreateRequest(
                1, 1, "test", "description", 1, List.of(1, 2), 1, true, 1, LabelColor.RED, LocalDate.now(), LocalDate.now()
        );

        given(workspaceRepository.findById(request.getWorkspaceId().longValue()))
                .willReturn(Optional.of(workspace));

        given(stateRepository.findByIdAndWorkspaceId(request.getStateId(), request.getWorkspaceId()))
                .willReturn(Optional.of(state));

        given(memberRepository.findByIdIn(request.getManagers())).willReturn(members);

        given(tagRepository.findByIdInAndWorkspaceId(request.getTag(), request.getWorkspaceId())).willReturn(Optional.of(tag));

        doReturn(task).when(taskRepository).save(any(Task.class));

        given(managerTaskRepository.save(any(ManagerTask.class))).willReturn(null);

        //when
        TaskDetailResponse response = taskCreateService.createTask(request);

        //then
        Assertions.assertThat(response.getTaskId()).isEqualTo("1");
        Assertions.assertThat(response.getCreator()).isEqualTo("creator1");
        Assertions.assertThat(response.getManagers()).hasSize(2);
        Assertions.assertThat(response.getManagers().get(0).getId()).isEqualTo("1");
        Assertions.assertThat(response.getManagers().get(0).getName()).isEqualTo("name1");
    }

//    @Test
//    @DisplayName("업무 수정 성공 테스트")
//    void updateTask() {
//        //given
//        TaskUpdateRequest request = new TaskUpdateRequest(
//                2, "updateT", "updateD", "updateC", List.of(5, 6, 7), List.of(10), true, 5, LocalDate.now(), LocalDate.now()
//        );
//
//        TaskConfiguration taskConfiguration = new TaskConfiguration(true, 1);
//        ScheduleRange scheduleRange = new ScheduleRange(LocalDate.now(), LocalDate.now());
//        Task task = new Task(1L, "title", "description", "creator1", "1,2,3,4", "20", taskConfiguration, scheduleRange);
//        task.changeWorkspace(new Workspace(1L, "title", "description", "imageUrl"));
//        task.changeState(new State(1L, "state1"));
//        //when
//        given(taskRepository.findById(1L)).willReturn(Optional.of(task));
//
//        given(memberRepository.findByIdIn(List.of(10)))
//                .willReturn(List.of(
//                        Member.builder().id(10L).build()
//                ));
//
//        given(tagRepository.findByIdInAndWorkspaceId(List.of(5, 6, 7), 1L))
//                .willReturn(List.of(
//                        new Tag(5L, "tag5", "####5"),
//                        new Tag(6L, "tag6", "####6"),
//                        new Tag(7L, "tag7", "####7")
//                ));
//
//        given(stateRepository.findByIdAndWorkspaceId(request.getStateId().longValue(), 1L))
//                .willReturn(Optional.of(new State(2L, "state2")));
//
//
//        //then
//        TaskUpdateResponse response = taskUpdateService.update(1, request);
//        Assertions.assertThat(response.getTaskId()).isEqualTo("1");
//        Assertions.assertThat(response.getCreator()).isEqualTo("updateC");
//        Assertions.assertThat(response.getManagers()).hasSize(1);
//        Assertions.assertThat(response.getManagers().get(0).getId()).isEqualTo("10");
//    }
}