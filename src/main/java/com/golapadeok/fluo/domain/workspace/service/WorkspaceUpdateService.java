package com.golapadeok.fluo.domain.workspace.service;

import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import com.golapadeok.fluo.domain.workspace.dto.request.WorkspaceUpdateRequest;
import com.golapadeok.fluo.domain.workspace.dto.response.WorkspaceResponse;
import com.golapadeok.fluo.domain.workspace.exception.NotFoundWorkspaceException;
import com.golapadeok.fluo.domain.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WorkspaceUpdateService {
    private final WorkspaceRepository workspaceRepository;

    @Transactional
    public WorkspaceResponse update(long workspaceId, WorkspaceUpdateRequest request) {
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(NotFoundWorkspaceException::new);

        workspace.changeWorkspace(workspace.toBuilder()
                .title(request.getTitle())
                .build());

        workspaceRepository.flush();
        return new WorkspaceResponse(workspace);
    }
}
