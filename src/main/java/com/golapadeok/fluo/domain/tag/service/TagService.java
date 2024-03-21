package com.golapadeok.fluo.domain.tag.service;

import com.golapadeok.fluo.domain.tag.domain.Tag;
import com.golapadeok.fluo.domain.tag.dto.request.TagCreateRequest;
import com.golapadeok.fluo.domain.tag.dto.response.TagCreateResponse;
import com.golapadeok.fluo.domain.tag.repository.TagRepository;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import com.golapadeok.fluo.domain.workspace.exception.NotFoundWorkspaceException;
import com.golapadeok.fluo.domain.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Service
@RequiredArgsConstructor
@Transactional
public class TagService {
    private final TagRepository tagRepository;
    private final WorkspaceRepository workspaceRepository;

    @Transactional(readOnly = true)
    public Object searchTags() {
        return ResponseEntity.ok(null);
    }

    public TagCreateResponse createTags(TagCreateRequest request) {
        final long workspaceId = request.getWorkspaceId();
        Workspace workspace = workspaceRepository.findById(workspaceId)
                .orElseThrow(NotFoundWorkspaceException::new);

        Tag tag = new Tag(request.getTagName(), request.getColorCode());
        tag.changeWorkspace(workspace);
        tag = tagRepository.save(tag);

        return TagCreateResponse.of(tag);
    }

    public Object deleteTags() {
        return ResponseEntity.ok(null);
    }
}
