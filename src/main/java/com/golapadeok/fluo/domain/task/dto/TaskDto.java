package com.golapadeok.fluo.domain.task.dto;

import com.golapadeok.fluo.domain.workspace.dto.StateDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class TaskDto {
    private final String id;
    private final String title;
    private final String description;
    private final String creator;
    private final List<String> manager;
    private final Boolean isPrivate;
    private final Integer priority;
    private final StateDto stateDto;
    private final String startDate;
    private final String endDate;
    private final String createDate;
    private final String updateDate;

    @Builder
    public TaskDto(String id, String title, String description, String creator, List<String> manager, Boolean isPrivate, Integer priority, StateDto stateDto, String startDate, String endDate, String createDate, String updateDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.creator = creator;
        this.manager = manager;
        this.isPrivate = isPrivate;
        this.priority = priority;
        this.stateDto = stateDto;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }
}
