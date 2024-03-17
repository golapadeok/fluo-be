package com.golapadeok.fluo.domain.workspace.service;

import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import com.golapadeok.fluo.domain.workspace.dto.request.WorkspaceCreateRequest;
import com.golapadeok.fluo.domain.workspace.dto.response.WorkspaceCreateResponse;
import com.golapadeok.fluo.domain.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class WorkspaceCreateService {
    private final WorkspaceRepository workspaceRepository;

    @Transactional
    public WorkspaceCreateResponse create(WorkspaceCreateRequest request) {
        final String title = request.getTitle();
        final String description = request.getDescription();

        Workspace workspace = new Workspace(title, description);
        workspaceRepository.save(workspace);
        return WorkspaceCreateResponse.of(
                workspace.getId().toString(),
                workspace.getTitle(),
                workspace.getDescription(),
                workspace.getCreateDate().toLocalDate());
    }
}
