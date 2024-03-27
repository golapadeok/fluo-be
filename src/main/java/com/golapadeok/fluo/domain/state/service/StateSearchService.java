package com.golapadeok.fluo.domain.state.service;

import com.golapadeok.fluo.domain.state.domain.State;
import com.golapadeok.fluo.domain.state.dto.response.StateSearchResponse;
import com.golapadeok.fluo.domain.state.exception.NotFoundStateException;
import com.golapadeok.fluo.domain.state.repository.StateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StateSearchService {
    private final StateRepository stateRepository;

    public StateSearchResponse search(Integer stateId) {
        final long id = stateId;
        State state = stateRepository.findById(id)
                .orElseThrow(NotFoundStateException::new);

        return new StateSearchResponse(state);
    }
}
