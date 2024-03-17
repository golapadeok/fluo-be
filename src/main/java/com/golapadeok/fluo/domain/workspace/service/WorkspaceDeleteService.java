package com.golapadeok.fluo.domain.workspace.service;

import com.golapadeok.fluo.domain.workspace.dto.response.WorkspaceDeleteResponse;
import com.golapadeok.fluo.domain.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkspaceDeleteService {
    private final WorkspaceRepository workspaceRepository;

    public WorkspaceDeleteResponse delete(Integer workspaceId) {
        final long id = workspaceId;
        workspaceRepository.deleteById(id);
        return WorkspaceDeleteResponse.of("워크스페이스 삭제에 성공했습니다.");
    }
}
