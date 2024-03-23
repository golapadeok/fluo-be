package com.golapadeok.fluo.domain.task.dto.response;

import com.golapadeok.fluo.domain.member.domain.Member;
import com.golapadeok.fluo.domain.state.dto.StateDto;
import com.golapadeok.fluo.domain.tag.domain.Tag;
import com.golapadeok.fluo.domain.tag.dto.TagDto;
import com.golapadeok.fluo.domain.task.domain.ScheduleRange;
import com.golapadeok.fluo.domain.task.domain.Task;
import com.golapadeok.fluo.domain.task.domain.TaskConfiguration;
import com.golapadeok.fluo.domain.task.dto.MemberDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class TaskSearchResponse {
    private final String taskId;
    private final String title;
    private final String description;
    private final String creator;
    private final StateDto state;
    private final List<MemberDto> managers;
    private final List<TagDto> tags;
    private final Boolean isPrivate;
    private final Integer priority;
    private final LocalDate startDate;
    private final LocalDate endDate;

    @Builder
    private TaskSearchResponse(String taskId, String title, String description, String creator, List<MemberDto> managers, List<TagDto> tags, TaskConfiguration configuration, ScheduleRange scheduleRange, StateDto state) {
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.creator = creator;
        this.managers = managers;
        this.tags = tags;
        this.isPrivate = configuration.getIsPrivate();
        this.priority = configuration.getPriority();
        this.startDate = scheduleRange.getStartDate().toLocalDate();
        this.endDate = scheduleRange.getEndDate().toLocalDate();
        this.state = state;
    }

    public static TaskSearchResponse of(Task task, List<Member> members, List<Tag> tags) {
        return TaskSearchResponse.builder()
                .taskId(task.getId().toString())
                .title(task.getTitle())
                .description(task.getDescription())
                .creator(task.getCreator())
                .managers(MemberDto.of(members))
                .tags(TagDto.of(tags))
                .state(StateDto.of(task.getState()))
                .configuration(task.getConfiguration())
                .scheduleRange(task.getScheduleRange())
                .build();
    }
}
