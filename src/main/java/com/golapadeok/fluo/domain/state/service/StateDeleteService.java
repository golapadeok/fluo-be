package com.golapadeok.fluo.domain.state.service;

import com.golapadeok.fluo.domain.state.domain.State;
import com.golapadeok.fluo.domain.state.exception.NotFoundStateException;
import com.golapadeok.fluo.domain.state.repository.StateRepository;
import com.golapadeok.fluo.domain.task.domain.ManagerTask;
import com.golapadeok.fluo.domain.task.domain.Task;
import com.golapadeok.fluo.domain.task.repository.ManagerTaskRepository;
import com.golapadeok.fluo.domain.task.repository.TaskRepository;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class StateDeleteService {
    private final StateRepository stateRepository;
    private final TaskRepository taskRepository;


    //기본값 삭제
    //state 속한 모든 업무 삭제
    @Transactional
    public void delete(long stateId) {
        State deleteState = stateRepository.findById(stateId)
                .orElseThrow(NotFoundStateException::new);

        List<Task> tasks = taskRepository.findByStateId(deleteState.getId());
        taskRepository.deleteAll(tasks);
        stateRepository.delete(deleteState);
    }
}
