package com.golapadeok.fluo.domain.task.service;

import com.golapadeok.fluo.domain.task.domain.Task;
import com.golapadeok.fluo.domain.task.dto.response.TaskDetailResponse;
import com.golapadeok.fluo.domain.task.exception.NotFoundTaskException;
import com.golapadeok.fluo.domain.task.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaskSearchService {
    private final TaskRepository taskRepository;

    @Transactional(readOnly = true)
    public TaskDetailResponse search(long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(NotFoundTaskException::new);

        return new TaskDetailResponse(task);
    }
}
