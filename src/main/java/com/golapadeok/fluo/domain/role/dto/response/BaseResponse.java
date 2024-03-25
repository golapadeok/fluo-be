package com.golapadeok.fluo.domain.role.dto.response;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class BaseResponse {
    private final Object items;

    public BaseResponse(Object items) {
        this.items = items;
    }

}
