package com.golapadeok.fluo.domain.workspace.dto.response;

import com.golapadeok.fluo.domain.tag.domain.Tag;
import com.golapadeok.fluo.domain.tag.dto.TagDto;
import lombok.Getter;

import java.util.List;

@Getter
public class WorkspaceSearchWithTagsResponse {
    private final List<TagDto> tags;

    private WorkspaceSearchWithTagsResponse(List<TagDto> tags) {
        this.tags = tags;
    }

    public static WorkspaceSearchWithTagsResponse of(List<Tag> tags) {
        return new WorkspaceSearchWithTagsResponse(TagDto.of(tags));
    }
}
