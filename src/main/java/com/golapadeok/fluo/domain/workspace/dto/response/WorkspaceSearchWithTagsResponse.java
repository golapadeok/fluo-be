package com.golapadeok.fluo.domain.workspace.dto.response;

import com.golapadeok.fluo.domain.tag.domain.Tag;
import com.golapadeok.fluo.domain.tag.dto.TagDto;
import lombok.Getter;

import java.util.List;

@Getter
public class WorkspaceSearchWithTagsResponse {
    private final List<TagDto> tags;

    public WorkspaceSearchWithTagsResponse(List<TagDto> tags) {
        this.tags = tags;
    }
}
