package com.golapadeok.fluo.domain.workspace.service;

import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import com.golapadeok.fluo.domain.workspace.dto.request.WorkspaceCreateRequest;
import com.golapadeok.fluo.domain.workspace.dto.response.WorkspaceCreateResponse;
import com.golapadeok.fluo.domain.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class WorkspaceCreateService {
    private final WorkspaceRepository workspaceRepository;

    @Transactional
    public WorkspaceCreateResponse create(WorkspaceCreateRequest request) {
        final String title = request.getTitle();
        final String description = request.getDescription();
        final String defaultImageUrl = "https://fluo-bucket.s3.ap-northeast-2.amazonaws.com/images/default_ea81d464-2433-4ca2-9281-691187752a05_S88da226f2926405ba7abe131b6e55a90w.png";
        final Workspace workspace = new Workspace(title, description, defaultImageUrl);

        workspaceRepository.save(workspace);
        return WorkspaceCreateResponse.of(workspace);
    }
}
