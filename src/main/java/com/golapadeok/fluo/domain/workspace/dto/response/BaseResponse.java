package com.golapadeok.fluo.domain.workspace.dto.response;

import lombok.Getter;

@Getter
public class BaseResponse {
    private final Object items;

    public BaseResponse(Object items) {
        this.items = items;
    }
}
