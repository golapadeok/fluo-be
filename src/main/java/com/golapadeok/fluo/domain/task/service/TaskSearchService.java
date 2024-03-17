package com.golapadeok.fluo.domain.task.service;

import com.golapadeok.fluo.domain.task.domain.Task;
import com.golapadeok.fluo.domain.task.dto.response.TaskSearchResponse;
import com.golapadeok.fluo.domain.task.exception.NotFoundTaskException;
import com.golapadeok.fluo.domain.task.repository.TaskRepository;
import com.golapadeok.fluo.domain.task.util.TaskMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaskSearchService {
    private final TaskRepository taskRepository;

    @Transactional(readOnly = true)
    public TaskSearchResponse search(Integer taskId) {
        final long id = taskId.longValue();
        Task task = taskRepository.findById(id)
                .orElseThrow(NotFoundTaskException::new);

        return TaskSearchResponse.of(task);
    }
}
