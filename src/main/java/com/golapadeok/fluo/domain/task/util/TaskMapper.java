package com.golapadeok.fluo.domain.task.util;

import com.golapadeok.fluo.domain.task.domain.Task;
import com.golapadeok.fluo.domain.task.dto.TaskDto;
import com.golapadeok.fluo.domain.task.dto.request.TaskRequest;
import com.golapadeok.fluo.domain.workspace.util.StateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
@RequiredArgsConstructor
public class TaskMapper {
    private final StateMapper stateMapper;

    public TaskDto convertTaskToDto(Task task) {
        return TaskDto.builder()
                .id(task.getId().toString())
                .title(task.getTitle())
                .description(task.getDescription())
                .creator(task.getCreator())
                .manager(Arrays.stream(task.getManager().split(",")).toList())
                .isPrivate(task.getIsPrivate())
                .priority(task.getPriority())
                .stateDto(stateMapper.convertStateToDto(task.getState()))
                .startDate(task.getStartDate().toString())
                .endDate(task.getEndDate().toString())
                .createDate(task.getCreateDate().toString())
                .updateDate(task.getUpdateDate().toString())
                .build();
    }

    public List<TaskDto> convertTaskToDto(List<Task> tasks) {
        Iterator<Task> iterator = tasks.iterator();
        List<TaskDto> results = new ArrayList<>();

        while (iterator.hasNext()) {
            Task task = iterator.next();
            TaskDto taskDto = convertTaskToDto(task);
            results.add(taskDto);
        }

        return Collections.unmodifiableList(results);
    }

    public Task convertTaskRequestToEntity(TaskRequest request) {
        return new Task(
                request.getTitle(),
                request.getDescription(),
                request.getCreator(),
                request.convertManager(),
                request.getIsPrivate(),
                request.getPriority(),
                request.convertStringStartDateToLocalDateTime(),
                request.convertStringEndDateToLocalDateTime()
        );
    }
}
