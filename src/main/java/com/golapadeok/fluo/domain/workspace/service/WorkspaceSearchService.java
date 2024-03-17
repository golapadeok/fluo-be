package com.golapadeok.fluo.domain.workspace.service;

import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import com.golapadeok.fluo.domain.workspace.dto.response.WorkspaceCreateResponse;
import com.golapadeok.fluo.domain.workspace.dto.response.WorkspaceSearchResponse;
import com.golapadeok.fluo.domain.workspace.exception.NotFoundWorkspaceException;
import com.golapadeok.fluo.domain.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WorkspaceSearchService {
    private final WorkspaceRepository workspaceRepository;

    @Transactional(readOnly = true)
    public WorkspaceSearchResponse search(Integer workspaceId) {
        Workspace workspace = workspaceRepository.findById(workspaceId.longValue())
                .orElseThrow(NotFoundWorkspaceException::new);

        return WorkspaceSearchResponse.of(workspace);
    }
}
