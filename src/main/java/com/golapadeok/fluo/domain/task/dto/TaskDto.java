package com.golapadeok.fluo.domain.task.dto;

import com.golapadeok.fluo.domain.state.dto.StateDto;
import com.golapadeok.fluo.domain.tag.dto.TagDto;
import com.golapadeok.fluo.domain.task.domain.ManagerTask;
import com.golapadeok.fluo.domain.task.domain.Task;
import com.golapadeok.fluo.domain.task.dto.response.TaskResponse;
import lombok.Getter;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Getter
public class TaskDto extends TaskResponse {
    private final List<MemberDto> managers;
    private final StateDto state;
    private final TagDto tag;

    private TaskDto(Task task) {
        super(task);
        this.managers = MemberDto.of(task.getManagers().stream().map(ManagerTask::getMember).toList());
        this.state = StateDto.of(task.getState());
        this.tag = TagDto.of(task.getTag());
    }

    public static TaskDto of(Task task) {
        Assert.notNull(task, "task must not be null");
        return new TaskDto(task);
    }

    public static List<TaskDto> of(List<Task> tasks) {
        Iterator<Task> iterator = tasks.iterator();
        List<TaskDto> results = new ArrayList<>();
        while (iterator.hasNext()) {
            Task task = iterator.next();
            results.add(of(task));
        }

        return Collections.unmodifiableList(results);
    }
}
