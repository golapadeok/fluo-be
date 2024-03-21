package com.golapadeok.fluo.domain.tag.exception;

import lombok.Getter;

@Getter
public enum TagStatus {
    NOT_FOUND_TAG(400, "존재하지 않는 태그 입니다.");

    private final int status;
    private final String message;

    TagStatus(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
