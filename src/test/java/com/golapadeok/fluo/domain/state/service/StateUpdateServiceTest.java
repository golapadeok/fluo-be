/*
package com.golapadeok.fluo.domain.state.service;

import com.golapadeok.fluo.domain.state.domain.State;
import com.golapadeok.fluo.domain.state.dto.request.StateUpdateRequest;
import com.golapadeok.fluo.domain.state.dto.response.StateUpdateResponse;
import com.golapadeok.fluo.domain.state.repository.StateRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@Transactional
@ExtendWith(MockitoExtension.class)
class StateUpdateServiceTest {

    @InjectMocks
    private StateUpdateService stateUpdateService;

    @Mock
    private StateRepository stateRepository;

    @Test
    void update() {
        //given
        StateUpdateRequest request = new StateUpdateRequest("update");
        State state = new State(1L, "state");

        //when
        given(stateRepository.findById(1L)).willReturn(Optional.of(state));

        //then
        StateUpdateResponse response = stateUpdateService.update(1, request);
        assertThat(response).isNotNull();
        assertThat(response.getName()).isEqualTo("update");
    }
}*/
