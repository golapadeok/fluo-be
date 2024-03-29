package com.golapadeok.fluo.domain.workspace.service;

import com.golapadeok.fluo.domain.state.domain.State;
import com.golapadeok.fluo.domain.state.repository.StateRepository;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import com.golapadeok.fluo.domain.workspace.dto.request.WorkspaceCreateRequest;
import com.golapadeok.fluo.domain.workspace.dto.response.WorkspaceResponse;
import com.golapadeok.fluo.domain.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WorkspaceCreateService {
    private final WorkspaceRepository workspaceRepository;
    private final StateRepository stateRepository;


    @Transactional
    public WorkspaceResponse create(WorkspaceCreateRequest request) {
        final String title = request.getTitle();
        final String description = request.getDescription();
        final String defaultImageUrl = "https://fluo-bucket.s3.ap-northeast-2.amazonaws.com/images/default_ea81d464-2433-4ca2-9281-691187752a05_S88da226f2926405ba7abe131b6e55a90w.png";
        final Workspace workspace = new Workspace(title, description, defaultImageUrl);
        workspaceRepository.save(workspace);

        State state = new State("default", true);
        state.changeWorkspace(workspace);
        stateRepository.save(state);

        return new WorkspaceResponse(workspace);
    }
}
