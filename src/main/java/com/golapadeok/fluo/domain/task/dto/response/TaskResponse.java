package com.golapadeok.fluo.domain.task.dto.response;

import com.golapadeok.fluo.domain.task.domain.Task;
import lombok.Getter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Getter
public class TaskResponse {
    private final String taskId;
    private final String title;
    private final String description;
    private final String creator;
    private final Boolean isPrivate;
    private final Integer priority;
    private final String startDate;
    private final String endDate;

    public TaskResponse(Task task) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US)
                .withZone(ZoneId.of("GMT"));

        this.taskId = task.getId().toString();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.creator = task.getCreator();
        this.isPrivate = task.getConfiguration().getIsPrivate();
        this.priority = task.getConfiguration().getPriority();
        this.startDate = task.getScheduleRange().getStartDate().format(formatter);
        this.endDate = task.getScheduleRange().getEndDate().format(formatter);
    }
}
