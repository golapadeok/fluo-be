package com.golapadeok.fluo.domain.tag.dto.response;

import lombok.Getter;

@Getter
public class TagDeleteResponse {
    private final String message;

    private TagDeleteResponse(String message) {
        this.message = message;
    }

    public static TagDeleteResponse of(String message) {
        return new TagDeleteResponse(message);
    }
}
