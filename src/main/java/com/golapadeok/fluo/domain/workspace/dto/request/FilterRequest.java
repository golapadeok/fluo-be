package com.golapadeok.fluo.domain.workspace.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FilterRequest {
    private Integer priority;
    private Integer stateId;
    private String manager;
    private String endDate;
    private String tag;
    private String projectName;
}
