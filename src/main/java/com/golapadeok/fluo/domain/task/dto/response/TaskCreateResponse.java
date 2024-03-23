package com.golapadeok.fluo.domain.task.dto.response;

import com.golapadeok.fluo.domain.member.domain.Member;
import com.golapadeok.fluo.domain.state.dto.StateDto;
import com.golapadeok.fluo.domain.task.domain.ScheduleRange;
import com.golapadeok.fluo.domain.task.domain.Task;
import com.golapadeok.fluo.domain.task.domain.TaskConfiguration;
import com.golapadeok.fluo.domain.task.dto.MemberDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TaskCreateResponse {
    private String taskId;
    private String title;
    private String description;
    private String creator;
    private StateDto state;
    private List<MemberDto> managers;
    private Boolean isPrivate;
    private Integer priority;
    private LocalDate startDate;
    private LocalDate endDate;

    @Builder
    private TaskCreateResponse(String taskId, String title, String description, String creator, StateDto state, List<MemberDto> managers, TaskConfiguration configuration, ScheduleRange scheduleRange) {
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.creator = creator;
        this.managers = managers;
        this.state = state;
        this.isPrivate = configuration.getIsPrivate();
        this.priority = configuration.getPriority();
        this.startDate = scheduleRange.getStartDate().toLocalDate();
        this.endDate = scheduleRange.getEndDate().toLocalDate();
    }

    public static TaskCreateResponse of(Task task, List<Member> managers) {
        return TaskCreateResponse.builder()
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
