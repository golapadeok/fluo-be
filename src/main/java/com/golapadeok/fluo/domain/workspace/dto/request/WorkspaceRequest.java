package com.golapadeok.fluo.domain.workspace.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class WorkspaceRequest {
    private String title;
    private String description;
}
