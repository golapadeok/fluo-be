package com.golapadeok.fluo.domain.task.dto.request;

import lombok.Getter;
import org.apache.logging.log4j.util.Strings;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class TaskRequest {
    private final Integer stateId;
    private final Integer workspaceId;
    private final String title;
    private final String description;
    private final String creator;
    private final List<String> manager;
    private final Integer priority;
    private final Boolean isPrivate;
    private final String startDate;
    private final String endDate;

    public TaskRequest(Integer stateId, String title, String description, String creator, List<String> manager, Integer priority, Boolean isPrivate, String startDate, String endDate, Integer workspaceId) {
        this.stateId = stateId;
        this.title = title;
        this.description = description;
        this.creator = creator;
        this.manager = manager;
        this.priority = priority;
        this.isPrivate = isPrivate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.workspaceId = workspaceId;
    }

    public String convertManager() {
        return Strings.join(manager, ',');
    }

    public LocalDateTime convertStringStartDateToLocalDateTime() {
        return LocalDateTime.parse(this.startDate + "T00:00:00");
    }

    public LocalDateTime convertStringEndDateToLocalDateTime() {
        return LocalDateTime.parse(this.endDate + "T00:00:00");
    }
}
