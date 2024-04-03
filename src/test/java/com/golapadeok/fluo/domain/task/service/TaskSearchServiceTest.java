package com.golapadeok.fluo.domain.task.service;

import com.golapadeok.fluo.domain.member.domain.Member;
import com.golapadeok.fluo.domain.state.domain.State;
import com.golapadeok.fluo.domain.tag.domain.Tag;
import com.golapadeok.fluo.domain.task.domain.*;
import com.golapadeok.fluo.domain.task.dto.response.TaskDetailResponse;
import com.golapadeok.fluo.domain.task.dto.response.TaskSearchResponse;
import com.golapadeok.fluo.domain.task.exception.NotFoundTaskException;
import com.golapadeok.fluo.domain.task.repository.TaskRepository;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@Transactional
@SpringBootTest
class TaskSearchServiceTest {

    @Autowired
    private TaskSearchService taskSearchService;

    @Autowired
    private TaskRepository taskRepository;

    @BeforeEach
    void init() {
        TaskConfiguration configuration = new TaskConfiguration(false, 5);
        ScheduleRange scheduleRange = new ScheduleRange(LocalDate.now(), LocalDate.now());
        Task task = new Task(1L, "task", "description", 1, LabelColor.BLUE, configuration, scheduleRange);
        taskRepository.save(task);
    }


    @Test
    @DisplayName("업무 단일조회 성공 케이스")
    void search() {
        TaskSearchResponse search = taskSearchService.search(1L);
        Assertions.assertThat(search.getTaskId()).isEqualTo(1L);
    }
}