package com.golapadeok.fluo.domain.tag.exception;

import lombok.Getter;

@Getter
public class NotFoundTagException extends RuntimeException {
    private final TagStatus tagStatus;

    public NotFoundTagException() {
        this.tagStatus = TagStatus.NOT_FOUND_TAG;
    }
}
