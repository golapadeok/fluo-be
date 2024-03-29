package com.golapadeok.fluo.domain.state.service;

import com.golapadeok.fluo.domain.state.domain.State;
import com.golapadeok.fluo.domain.state.dto.response.StateSearchResponse;
import com.golapadeok.fluo.domain.state.repository.StateRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@Transactional
@ExtendWith(MockitoExtension.class)
class StateSearchServiceTest {
    @InjectMocks
    private StateSearchService stateSearchService;

    @Mock
    private StateRepository stateRepository;

    @Test
    @DisplayName("상태 단일조회 성공 케이스")
    void search() {
        //given
        State state = new State(1L, "state", true);

        //when
        given(stateRepository.findById(1L)).willReturn(Optional.of(state));

        //then
        StateSearchResponse search = stateSearchService.search(1);

        assertThat(search).isNotNull();
        assertThat(search.getStateId()).isEqualTo("1");
        assertThat(search.getName()).isEqualTo("state");
    }
}