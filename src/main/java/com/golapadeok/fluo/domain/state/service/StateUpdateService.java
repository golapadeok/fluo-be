package com.golapadeok.fluo.domain.state.service;

import com.golapadeok.fluo.domain.state.domain.State;
import com.golapadeok.fluo.domain.state.dto.request.StateUpdateRequest;
import com.golapadeok.fluo.domain.state.dto.response.StateUpdateResponse;
import com.golapadeok.fluo.domain.state.exception.NotFoundStateException;
import com.golapadeok.fluo.domain.state.repository.StateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StateUpdateService {
    private final StateRepository stateRepository;

    @Transactional
    public StateUpdateResponse update(Integer stateId, StateUpdateRequest request) {
        final long id = stateId;
        State state = stateRepository.findById(id)
                .orElseThrow(NotFoundStateException::new);

        state.changeState(request);
        stateRepository.flush();

        return StateUpdateResponse.of(state);
    }
}
