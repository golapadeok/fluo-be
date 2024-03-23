package com.golapadeok.fluo.domain.task.dto.response;

import com.golapadeok.fluo.domain.member.domain.Member;
import com.golapadeok.fluo.domain.state.dto.StateDto;
import com.golapadeok.fluo.domain.task.domain.ScheduleRange;
import com.golapadeok.fluo.domain.task.domain.Task;
import com.golapadeok.fluo.domain.task.domain.TaskConfiguration;
import com.golapadeok.fluo.domain.task.dto.MemberDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Getter
public class TaskUpdateResponse {
    private final String taskId;
    private final String title;
    private final String description;
    private final String creator;
    private final StateDto state;
    private final List<MemberDto> managers;
    private final Boolean isPrivate;
    private final Integer priority;
    private final LocalDate startDate;
    private final LocalDate endDate;

    @Builder
    private TaskUpdateResponse(String taskId, String title, String description, String creator, StateDto state, List<MemberDto> managers, TaskConfiguration configuration, ScheduleRange scheduleRange) {
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.creator = creator;
        this.state = state;
        this.managers = managers;
        this.isPrivate = configuration.getIsPrivate();
        this.priority = configuration.getPriority();
        this.startDate = scheduleRange.getStartDate().toLocalDate();
        this.endDate = scheduleRange.getEndDate().toLocalDate();
    }

    public static TaskUpdateResponse of(Task task, List<Member> managers) {
        return TaskUpdateResponse.builder()
                .taskId(task.getId().toString())
                .title(task.getTitle())
                .description(task.getDescription())
                .creator(task.getCreator())
                .state(StateDto.of(task.getState()))
                .managers(MemberDto.of(managers))
                .configuration(task.getConfiguration())
                .scheduleRange(task.getScheduleRange())
                .build();
    }
}
