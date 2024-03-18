package com.golapadeok.fluo.domain.state.service;

import com.golapadeok.fluo.domain.state.domain.State;
import com.golapadeok.fluo.domain.state.dto.request.StateCreateRequest;
import com.golapadeok.fluo.domain.state.dto.response.StateCreateResponse;
import com.golapadeok.fluo.domain.state.repository.StateRepository;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import com.golapadeok.fluo.domain.workspace.exception.NotFoundWorkspaceException;
import com.golapadeok.fluo.domain.workspace.repository.WorkspaceRepository;
import com.golapadeok.fluo.domain.workspace.service.WorkspaceSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StateCreateService {
    private final StateRepository stateRepository;
    private final WorkspaceRepository workspaceRepository;

    @Transactional
    public StateCreateResponse create(StateCreateRequest request) {
        final long workspaceId = request.getWorkspaceId();
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(NotFoundWorkspaceException::new);

        State state = new State(request.getName());
        state.changeWorkspace(workspace);

        return StateCreateResponse.of(stateRepository.save(state));
    }
}
