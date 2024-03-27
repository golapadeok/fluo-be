package com.golapadeok.fluo.domain.task.service;

import com.golapadeok.fluo.domain.member.repository.MemberRepository;
import com.golapadeok.fluo.domain.tag.repository.TagRepository;
import com.golapadeok.fluo.domain.task.domain.Task;
import com.golapadeok.fluo.domain.task.dto.response.TaskDetailResponse;
import com.golapadeok.fluo.domain.task.exception.NotFoundTaskException;
import com.golapadeok.fluo.domain.task.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TaskSearchService {
    private final TagRepository tagRepository;
    private final TaskRepository taskRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public TaskDetailResponse search(Integer taskId) {
        Task task = findTaskById(taskId);
        return new TaskDetailResponse(task);
    }

    private Task findTaskById(long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(NotFoundTaskException::new);
    }
}
