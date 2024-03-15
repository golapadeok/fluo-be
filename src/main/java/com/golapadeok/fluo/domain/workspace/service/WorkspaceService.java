package com.golapadeok.fluo.domain.workspace.service;

import com.golapadeok.fluo.domain.workspace.domain.State;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import com.golapadeok.fluo.domain.workspace.dto.*;
import com.golapadeok.fluo.domain.workspace.dto.request.StateRequest;
import com.golapadeok.fluo.domain.workspace.dto.request.WorkspaceRequest;
import com.golapadeok.fluo.domain.workspace.repository.StateRepository;
import com.golapadeok.fluo.domain.workspace.repository.WorkspaceRepository;
import com.golapadeok.fluo.domain.workspace.dto.response.BaseResponse;
import com.golapadeok.fluo.domain.workspace.util.StateMapper;
import com.golapadeok.fluo.domain.workspace.util.WorkspaceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkspaceService {
    private final WorkspaceRepository workspaceRepository;
    private final WorkspaceMapper workspaceMapper;

    private final StateRepository stateRepository;
    private final StateMapper stateMapper;

    public BaseResponse getWorkspaces() {
        List<Workspace> workspaces = workspaceRepository.findAll();
        List<WorkspaceDto> results = workspaceMapper.convertWorkspaceToDto(workspaces);
        return new BaseResponse(results);
    }

    public WorkspaceDto getWorkspace(Integer workspaceId) {
        long id = workspaceId.longValue();
        Workspace workspace = workspaceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 워크스페이스입니다."));
        return workspaceMapper.convertWorkspaceToDto(workspace);
    }

    @Transactional
    public WorkspaceDto createWorkspace(WorkspaceRequest request) {
        Workspace workspace = workspaceMapper.convertWorkspaceRequestToEntity(request);
        workspaceRepository.save(workspace);
        return workspaceMapper.convertWorkspaceToDto(workspace);
    }


    @Transactional
    public WorkspaceIdDto deleteWorkspace(Integer workspaceId) {
        workspaceRepository.deleteById(workspaceId.longValue());
        return new WorkspaceIdDto(workspaceId.toString());
    }

    @Transactional
    public WorkspaceWithStatesDto getWorkspaceWithStates(Integer workspaceId) {
        Workspace workspace = workspaceRepository.findById(workspaceId.longValue())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 워크스페이스입니다."));

        List<State> states = workspace.getStates();
        List<StateDto> stateDtos = stateMapper.convertStateToDto(states);
        return new WorkspaceWithStatesDto(workspaceId.toString(), workspace.getTitle(), workspace.getDescription(), stateDtos);
    }

    @Transactional
    public StateDto createState(Integer workspaceId, StateRequest request) {
        Workspace workspace = workspaceRepository.findById(workspaceId.longValue())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 워크스페이스입니다."));

        State state = new State(request.getName());
        state.changeWorkspace(workspace);
        stateRepository.save(state);
        return stateMapper.convertStateToDto(state);
    }
}
