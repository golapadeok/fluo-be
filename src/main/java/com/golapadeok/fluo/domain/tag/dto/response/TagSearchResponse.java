package com.golapadeok.fluo.domain.tag.dto.response;

import com.golapadeok.fluo.domain.tag.domain.Tag;
import lombok.Getter;

@Getter
public class TagSearchResponse {
    private final String tagId;
    private final String tagName;
    private final String colorCode;

    private TagSearchResponse(String tagId, String tagName, String colorCode) {
        this.tagId = tagId;
        this.tagName = tagName;
        this.colorCode = colorCode;
    }

    public static TagSearchResponse of(Tag tag) {
        return new TagSearchResponse(
                tag.getId().toString(),
                tag.getTagName(),
                tag.getColorCode()
        );
    }
}
