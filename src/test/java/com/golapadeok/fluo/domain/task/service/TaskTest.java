/*
package com.golapadeok.fluo.domain.task.service;

import com.golapadeok.fluo.domain.state.domain.State;
import com.golapadeok.fluo.domain.tag.domain.Tag;
import com.golapadeok.fluo.domain.task.domain.LabelColor;
import com.golapadeok.fluo.domain.task.domain.ScheduleRange;
import com.golapadeok.fluo.domain.task.domain.Task;
import com.golapadeok.fluo.domain.task.domain.TaskConfiguration;
import com.golapadeok.fluo.domain.task.dto.response.TaskSearchResponse;
import com.golapadeok.fluo.domain.task.repository.TaskRepository;
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
import java.util.Optional;


@Transactional
@ExtendWith(MockitoExtension.class)
class TaskTest {

    @InjectMocks
    private TaskSearchService taskSearchService;

    @Mock
    private TaskRepository taskRepository;

    @Test
    @DisplayName("업무 단일조회 성공 케이스")
    void search() {
        //given
        Task task = createTask();

        BDDMockito.given(taskRepository.findById(1L)).willReturn(Optional.of(task));

        //when
        TaskSearchResponse search = tasTkSearchService.search(1L);

        //then
        Assertions.assertThat(search.getTaskId()).isEqualTo(1L);
    }

    private Task createTask(Tag tag,) {
        TaskConfiguration configuration = new TaskConfiguration(false, 5);
        ScheduleRange scheduleRange = new ScheduleRange(LocalDate.now(), LocalDate.now());
        Task task = new Task(1L, "task", "description", 1, LabelColor.BLUE, configuration, scheduleRange);
        task.changeTag(tag);
        task.
        return task;
    }

    private State createState() {
        State state = new State();
    }
}
*/
