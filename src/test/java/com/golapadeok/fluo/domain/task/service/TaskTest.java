package com.golapadeok.fluo.domain.task.service;

import com.golapadeok.fluo.domain.member.repository.MemberRepository;
import com.golapadeok.fluo.domain.state.domain.State;
import com.golapadeok.fluo.domain.state.repository.StateRepository;
import com.golapadeok.fluo.domain.tag.domain.Tag;
import com.golapadeok.fluo.domain.tag.dto.ColorCode;
import com.golapadeok.fluo.domain.tag.repository.TagRepository;
import com.golapadeok.fluo.domain.task.domain.LabelColor;
import com.golapadeok.fluo.domain.task.domain.ScheduleRange;
import com.golapadeok.fluo.domain.task.domain.Task;
import com.golapadeok.fluo.domain.task.domain.TaskConfiguration;
import com.golapadeok.fluo.domain.task.dto.request.TaskUpdateRequest;
import com.golapadeok.fluo.domain.task.dto.response.TaskDetailResponse;
import com.golapadeok.fluo.domain.task.dto.response.TaskSearchResponse;
import com.golapadeok.fluo.domain.task.exception.NotFoundTaskException;
import com.golapadeok.fluo.domain.task.repository.ManagerTaskRepository;
import com.golapadeok.fluo.domain.task.repository.TaskRepository;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import org.assertj.core.api.Assertions;
import org.hibernate.jdbc.Work;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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


@Transactional
@ExtendWith(MockitoExtension.class)
class TaskTest {


    @Nested
    @DisplayName("업무 조회 테스트")
    class Search {
        @InjectMocks
        private TaskSearchService taskSearchService;

        @Mock
        private TaskRepository taskRepository;

        @Test
        @DisplayName("업무 단일조회 성공 케이스")
        void search() {
            //given
            Workspace workspace = createWorkspace();
            Tag tag = createTag(workspace);
            State state = createState(workspace);
            Task task = createTask(tag, state, workspace);

            BDDMockito.given(taskRepository.findById(1L)).willReturn(Optional.of(task));

            //when
            TaskSearchResponse search = taskSearchService.search(1L);

            //then
            Assertions.assertThat(search.getTaskId()).isEqualTo("1");
        }
    }

    @Nested
    @DisplayName("업무 수정 테스트")
    class Update {
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
        @DisplayName("업무 업데이트 성공 케이스")
        void update() {
            //given
            Workspace workspace = createWorkspace();
            Tag updateTag = updateTag(workspace);
            State updateState = updateState(workspace);
            Task updateTask = updateTask(updateTag, updateState, workspace);

            Tag tag = createTag(workspace);
            State state = createState(workspace);
            Task task = createTask(tag, state, workspace);

            TaskUpdateRequest updateRequest = createUpdateRequest(updateTask);

            BDDMockito.given(taskRepository.findById(task.getId())).willReturn(Optional.of(task));
            BDDMockito.given(tagRepository.findById(updateTag.getId())).willReturn(Optional.of(updateTag));
            BDDMockito.given(stateRepository.findById(updateState.getId())).willReturn(Optional.of(updateState));

            //when
            TaskDetailResponse response = taskUpdateService.update(1L, updateRequest);

            //then
            Assertions.assertThat(response.getTaskId()).isEqualTo("1");
            Assertions.assertThat(response.getTitle()).isEqualTo(updateTask.getTitle());
            Assertions.assertThat(response.getIsPrivate()).isEqualTo(updateTask.getConfiguration().getIsPrivate());
            Assertions.assertThat(response.getPriority()).isEqualTo(updateTask.getConfiguration().getPriority());
            Assertions.assertThat(response.getCreator()).isEqualTo(updateTask.getCreator());
            Assertions.assertThat(response.getLabelColor()).isEqualTo(updateTask.getLabelColor());
        }

        private TaskUpdateRequest createUpdateRequest(Task updateTask) {

            return new TaskUpdateRequest(
                    updateTask.getState().getId().intValue(),
                    updateTask.getTitle(),
                    updateTask.getDescription(),
                    updateTask.getCreator(),
                    updateTask.getTag().getId().intValue(),
                    List.of(1, 2, 3),
                    updateTask.getConfiguration().getIsPrivate(),
                    updateTask.getConfiguration().getPriority(),
                    updateTask.getLabelColor(),
                    updateTask.getScheduleRange().getStartDate().toLocalDate(),
                    updateTask.getScheduleRange().getEndDate().toLocalDate()
            );
        }

        private Task updateTask(Tag tag, State state, Workspace workspace) {
            TaskConfiguration configuration = new TaskConfiguration(false, 10);
            ScheduleRange scheduleRange = new ScheduleRange(LocalDate.now(), LocalDate.now());
            Task task = new Task(2L, "updateTask", "updateDescription", 2, LabelColor.PINK, configuration, scheduleRange);
            task.changeTag(tag);
            task.changeState(state);
            task.changeWorkspace(workspace);
            return task;
        }

        private State updateState(Workspace workspace) {
            State state = new State(2L, "updateState", true);
            state.changeWorkspace(workspace);
            return state;
        }

        private Tag updateTag(Workspace workspace) {
            Tag tag = new Tag(2L, "updateTag", ColorCode.BLUE);
            tag.changeWorkspace(workspace);
            return tag;
        }
    }

    private static Workspace createWorkspace() {
        Workspace workspace = new Workspace(1L, "title", "description", "imageUrl", "creator");
        workspace.changeInvitationCode("123456");
        return workspace;
    }

    private static Task createTask(Tag tag, State state, Workspace workspace) {
        TaskConfiguration configuration = new TaskConfiguration(false, 5);
        ScheduleRange scheduleRange = new ScheduleRange(LocalDate.now(), LocalDate.now());
        Task task = new Task(1L, "task", "description", 1, LabelColor.BLUE, configuration, scheduleRange);
        task.changeTag(tag);
        task.changeState(state);
        task.changeWorkspace(workspace);
        return task;
    }

    private static State createState(Workspace workspace) {
        State state = new State(1L, "state1", false);
        state.changeWorkspace(workspace);
        return state;
    }

    private static Tag createTag(Workspace workspace) {
        Tag tag = new Tag(1L, "tag1", ColorCode.INDIGO);
        tag.changeWorkspace(workspace);
        return tag;
    }


}
