package com.golapadeok.fluo.domain.tag.service;

import com.golapadeok.fluo.domain.tag.domain.Tag;
import com.golapadeok.fluo.domain.tag.dto.request.TagCreateRequest;
import com.golapadeok.fluo.domain.tag.dto.response.TagCreateResponse;
import com.golapadeok.fluo.domain.tag.dto.response.TagDeleteResponse;
import com.golapadeok.fluo.domain.tag.dto.response.TagSearchResponse;
import com.golapadeok.fluo.domain.tag.exception.NotFoundTagException;
import com.golapadeok.fluo.domain.tag.repository.TagRepository;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import com.golapadeok.fluo.domain.workspace.exception.NotFoundWorkspaceException;
import com.golapadeok.fluo.domain.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TagService {
    private final TagRepository tagRepository;
    private final WorkspaceRepository workspaceRepository;

    @Transactional(readOnly = true)
    public TagSearchResponse searchTags(Integer tagId) {
        Tag tag = tagRepository.findById(tagId.longValue())
                .orElseThrow(NotFoundTagException::new);

        return TagSearchResponse.of(tag);
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

    public TagDeleteResponse deleteTags(Integer tagId) {
        Tag tag = tagRepository.findById(tagId.longValue())
                .orElseThrow(NotFoundTagException::new);

        tagRepository.delete(tag);
        return TagDeleteResponse.of("태그 삭제에 성공했습니다.");
    }
}
