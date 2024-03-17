package com.golapadeok.fluo.domain.task.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class TaskConfiguration {
    private String creator;
    private String manager;
    private Boolean isPrivate;
    private Integer priority;

    public TaskConfiguration(String creator, String manager, Boolean isPrivate, Integer priority) {
        this.creator = creator;
        this.manager = manager;
        this.isPrivate = isPrivate;
        this.priority = priority;
    }
}
