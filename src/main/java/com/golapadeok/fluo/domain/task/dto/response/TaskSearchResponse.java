package com.golapadeok.fluo.domain.task.dto.response;


import com.golapadeok.fluo.domain.state.dto.StateDto;
import com.golapadeok.fluo.domain.tag.dto.TagDto;
import com.golapadeok.fluo.domain.task.domain.Task;
import com.golapadeok.fluo.domain.task.dto.MemberDto;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class TaskSearchResponse extends TaskResponse {

    private final String description;
    private final StateDto state;
    private final TagDto tag;
    private final List<MemberDto> managers;

    @Builder
    public TaskSearchResponse(Task task) {
        super(task);
        description = task.getDescription();
        this.managers = task.getManagers().stream()
                .map(managerTask -> MemberDto.of(managerTask.getMember()))
                .toList();
        this.tag = TagDto.of(task.getTag());
        this.state = StateDto.of(task.getState());
    }
}
