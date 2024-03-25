package com.golapadeok.fluo.domain.task.service;

import com.golapadeok.fluo.domain.task.dto.response.TaskDeleteResponse;
import com.golapadeok.fluo.domain.task.repository.TaskRepository;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaskDeleteService {
    private final TaskRepository taskRepository;

    @Transactional
    public TaskDeleteResponse delete(Integer taskId) {
        long id = taskId.longValue();
        taskRepository.findById(id)
                .ifPresent(task -> {
                    Workspace workspace = task.getWorkspace();
                    workspace.getTasks().remove(task);
                    task.changeWorkspace(null);
                    taskRepository.deleteById(id);
                });
        
        return TaskDeleteResponse.of("삭제에 성공했습니다.");
    }
}
