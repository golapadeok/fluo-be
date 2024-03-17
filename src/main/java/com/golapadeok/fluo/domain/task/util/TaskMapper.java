package com.golapadeok.fluo.domain.task.util;

import com.golapadeok.fluo.domain.task.domain.Task;
import com.golapadeok.fluo.domain.task.dto.response.TaskCreateResponse;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    public TaskCreateResponse convertResponseToEntity(Task task) {
        return TaskCreateResponse.of(task);
    }
}
