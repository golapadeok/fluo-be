package com.golapadeok.fluo.domain.workspace.util;

import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import com.golapadeok.fluo.domain.workspace.dto.WorkspaceDto;
import com.golapadeok.fluo.domain.workspace.dto.request.WorkspaceRequest;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Component
public class WorkspaceMapper {

    public WorkspaceDto convertWorkspaceToDto(Workspace workspace) {
        return new WorkspaceDto(
                workspace.getId().toString(),
                workspace.getTitle(),
                workspace.getDescription()
        );
    }

    public List<WorkspaceDto> convertWorkspaceToDto(List<Workspace> workspaces) {
        Iterator<Workspace> iterator = workspaces.iterator();
        List<WorkspaceDto> results = new ArrayList<>();

        while (iterator.hasNext()) {
            Workspace workspace = iterator.next();
            WorkspaceDto workspaceDto = convertWorkspaceToDto(workspace);
            results.add(workspaceDto);
        }

        return Collections.unmodifiableList(results);
    }

    public Workspace convertWorkspaceRequestToEntity(WorkspaceRequest request) {
        return new Workspace(request.getTitle(), request.getDescription());
    }
}
