package com.golapadeok.fluo.domain.member.service;

import com.golapadeok.fluo.domain.member.dto.response.InvitationWithWorkspaceInfoResponse;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import com.golapadeok.fluo.domain.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberInvitationWorkspaceService {

    private final WorkspaceRepository workspaceRepository;

    @Transactional(readOnly = true)
    public InvitationWithWorkspaceInfoResponse searchWorkspaceByInvitationCode(String invitationCode) {
        Workspace workspace = this.workspaceRepository.findByInvitationCode(invitationCode)
                .orElseThrow(() -> new IllegalArgumentException("잘못된 초대코드입니다. 다시 입력해주세요."));

        return InvitationWithWorkspaceInfoResponse.builder()
                .workspaceId(String.valueOf(workspace.getId()))
                .title(workspace.getTitle())
                .intro(workspace.getDescription())
                .build();
    }

}
