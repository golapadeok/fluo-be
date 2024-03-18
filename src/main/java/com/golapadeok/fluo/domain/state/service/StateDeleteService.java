package com.golapadeok.fluo.domain.state.service;

import com.golapadeok.fluo.domain.state.domain.State;
import com.golapadeok.fluo.domain.state.exception.NotFoundStateException;
import com.golapadeok.fluo.domain.state.repository.StateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StateDeleteService {
    private final StateRepository stateRepository;

    @Transactional
    public void delete(Integer stateId) {
        final long id = stateId;
        State state = stateRepository.findById(id)
                .orElseThrow(NotFoundStateException::new);

        // TODO KDY 나중에 CACADE 추가할 것
        stateRepository.delete(state);
    }
}
