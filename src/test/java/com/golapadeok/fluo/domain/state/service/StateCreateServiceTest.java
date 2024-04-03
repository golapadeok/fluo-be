/*
package com.golapadeok.fluo.domain.state.service;

import com.golapadeok.fluo.domain.state.domain.State;
import com.golapadeok.fluo.domain.state.dto.request.StateCreateRequest;
import com.golapadeok.fluo.domain.state.dto.response.StateCreateResponse;
import com.golapadeok.fluo.domain.state.repository.StateRepository;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import com.golapadeok.fluo.domain.workspace.repository.WorkspaceRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;

@Transactional
@ExtendWith(MockitoExtension.class)
class StateCreateServiceTest {
    @InjectMocks
    private StateCreateService stateCreateService;

    @Mock
    private StateRepository stateRepository;
    @Mock
    private WorkspaceRepository workspaceRepository;


    @Test
    @DisplayName("상태 생성 성공 케이스")
    void create() {
        //given
        StateCreateRequest stateCreateRequest = new StateCreateRequest(1, "state");

        Workspace workspace = new Workspace(1L, "title", "title", "description");

        State state = new State(1L, "state", true);
        state.changeWorkspace(workspace);

        //when
        given(stateRepository.save(any(State.class))).willReturn(state);

        given(workspaceRepository.findById(1L)).willReturn(Optional.of(workspace));

        //then
        StateCreateResponse stateCreateResponse = stateCreateService.create(stateCreateRequest);
        assertThat(stateCreateResponse.getStateId()).isEqualTo("1");
        assertThat(stateCreateResponse.getName()).isEqualTo("state");
    }
}*/
