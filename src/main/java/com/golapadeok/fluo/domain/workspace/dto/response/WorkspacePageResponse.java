package com.golapadeok.fluo.domain.workspace.dto.response;

import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Getter
public class WorkspacePageResponse extends WorkspaceResponse {

    private WorkspacePageResponse(Workspace workspace) {
        super(workspace);
    }

    public static List<WorkspacePageResponse> of(List<Workspace> workspaces) {
        Iterator<Workspace> iterator = workspaces.iterator();
        List<WorkspacePageResponse> results = new ArrayList<>();
        while (iterator.hasNext()) {
            Workspace workspace = iterator.next();
            WorkspacePageResponse response = new WorkspacePageResponse(workspace);
            results.add(response);
        }

        return Collections.unmodifiableList(results);
    }
}
