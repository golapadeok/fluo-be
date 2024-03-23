package com.golapadeok.fluo.domain.task.service;

import com.golapadeok.fluo.domain.member.domain.Member;
import com.golapadeok.fluo.domain.member.repository.MemberRepository;
import com.golapadeok.fluo.domain.task.domain.Task;
import com.golapadeok.fluo.domain.task.dto.response.TaskSearchResponse;
import com.golapadeok.fluo.domain.task.exception.NotFoundTaskException;
import com.golapadeok.fluo.domain.task.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskSearchService {
    private final TaskRepository taskRepository;
    private final MemberRepository memberRepository;

    @Transactional(readOnly = true)
    public TaskSearchResponse search(Integer taskId) {
        Task task = findTaskById(taskId);
        List<Integer> managers = extractManagerId(task.getManager());
        List<Member> members = memberRepository.findByIdIn(managers);
        return TaskSearchResponse.of(task, members);
    }

    private static List<Integer> extractManagerId(String manager) {
        if (manager.isEmpty())
            return Collections.emptyList();

        List<String> list = Arrays.asList(manager.split(","));
        return list.stream()
                .map(Integer::parseInt)
                .toList();
    }

    private Task findTaskById(long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(NotFoundTaskException::new);
    }
}
