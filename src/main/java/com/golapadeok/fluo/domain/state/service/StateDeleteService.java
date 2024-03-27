package com.golapadeok.fluo.domain.state.service;

import com.golapadeok.fluo.domain.state.domain.State;
import com.golapadeok.fluo.domain.state.exception.NotFoundStateException;
import com.golapadeok.fluo.domain.state.repository.StateRepository;
import com.golapadeok.fluo.domain.task.domain.Task;
import com.golapadeok.fluo.domain.task.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StateDeleteService {
    private final StateRepository stateRepository;
    private final TaskRepository taskRepository;

    @Transactional
    public void delete(long stateId) {
        State deleteState = stateRepository.findById(stateId)
                .orElseThrow(NotFoundStateException::new);

        if (Boolean.TRUE.equals(deleteState.getIsDefault()))
            return;

        State defaultState = stateRepository.findByIsDefault(true)
                .orElseThrow(NotFoundStateException::new);

        List<Task> tasks = taskRepository.findByStateId(stateId);
        for (Task task : tasks) {
            task.changeState(defaultState);
        }

        stateRepository.delete(deleteState);
    }
}
