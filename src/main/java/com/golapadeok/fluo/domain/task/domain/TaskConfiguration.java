package com.golapadeok.fluo.domain.task.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class TaskConfiguration {
    private Boolean isPrivate;
    private Integer priority;

    public TaskConfiguration(Boolean isPrivate, Integer priority) {
        this.isPrivate = isPrivate;
        this.priority = priority;
    }
}
