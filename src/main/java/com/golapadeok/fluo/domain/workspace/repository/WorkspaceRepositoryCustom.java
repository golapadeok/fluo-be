package com.golapadeok.fluo.domain.workspace.repository;

import com.golapadeok.fluo.domain.task.domain.Task;
import com.golapadeok.fluo.domain.workspace.dto.CustomPageImpl;
import com.golapadeok.fluo.domain.workspace.dto.request.CursorPageRequest;
import com.golapadeok.fluo.domain.workspace.dto.request.FilterRequest;

public interface WorkspaceRepositoryCustom {
    CustomPageImpl<Task> searchPageTasks(Integer workspaceId, CursorPageRequest pageRequest, FilterRequest filterRequest);
}

