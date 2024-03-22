package com.golapadeok.fluo.domain.workspace.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// TODO KDY 확실하지 않음 수정 필요
public class ConditionRequest {
    private Integer priority;
    private Integer stateId;
    private String manager;
    private String endDate;
    private String tagName;
    private String taskTitle;
}
