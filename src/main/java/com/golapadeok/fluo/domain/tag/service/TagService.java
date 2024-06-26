package com.golapadeok.fluo.domain.tag.service;

import com.golapadeok.fluo.domain.tag.domain.Tag;
import com.golapadeok.fluo.domain.tag.dto.request.TagCreateRequest;
import com.golapadeok.fluo.domain.tag.dto.request.TagUpdateRequest;
import com.golapadeok.fluo.domain.tag.dto.response.TagCreateResponse;
import com.golapadeok.fluo.domain.tag.dto.response.TagDeleteResponse;
import com.golapadeok.fluo.domain.tag.dto.response.TagSearchResponse;
import com.golapadeok.fluo.domain.tag.exception.NotFoundTagException;
import com.golapadeok.fluo.domain.tag.repository.TagRepository;
import com.golapadeok.fluo.domain.task.domain.Task;
import com.golapadeok.fluo.domain.task.repository.TaskRepository;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import com.golapadeok.fluo.domain.workspace.exception.NotFoundWorkspaceException;
import com.golapadeok.fluo.domain.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TagService {
    private final TagRepository tagRepository;
    private final WorkspaceRepository workspaceRepository;
    private final TaskRepository taskRepository;

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

        Tag tag = new Tag(request.getName(), request.getColorCode());
        tag.changeWorkspace(workspace);
        tag = tagRepository.save(tag);

        return TagCreateResponse.of(tag);
    }

    public TagCreateResponse updateTags(long tagId, TagUpdateRequest request) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new NotFoundTagException());
        tag.changeTag(tag.toBuilder()
                .tagName(request.getName())
                .colorCode(request.getColorCode())
                .build());
        
        tagRepository.flush();
        return TagCreateResponse.of(tag);
    }

    public TagDeleteResponse deleteTags(Integer tagId) {

        Tag tag = tagRepository.findById(tagId.longValue())
                .orElseThrow(NotFoundTagException::new);

        List<Task> tasks = taskRepository.findByTagId(tagId);
        tasks.forEach(task -> task.changeTag(null));

        tag.getWorkspace().getTags().remove(tag);
        tagRepository.delete(tag);
        return TagDeleteResponse.of("태그 삭제에 성공했습니다.");
    }
}
