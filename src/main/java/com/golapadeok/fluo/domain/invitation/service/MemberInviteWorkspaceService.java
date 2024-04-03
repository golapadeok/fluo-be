package com.golapadeok.fluo.domain.invitation.service;

import com.golapadeok.fluo.domain.invitation.dto.response.InvitationWithWorkspaceInfoResponse;
import com.golapadeok.fluo.domain.invitation.exception.InvitationErrorStatus;
import com.golapadeok.fluo.domain.invitation.exception.InvitationException;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import com.golapadeok.fluo.domain.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberInviteWorkspaceService {

    private final WorkspaceRepository workspaceRepository;

    @Transactional(readOnly = true)
    public InvitationWithWorkspaceInfoResponse searchWorkspaceByInvitationCode(String invitationsCode) {
        Workspace workspace = this.workspaceRepository.findByInvitationCode(invitationsCode)
                .orElseThrow(() -> new InvitationException(InvitationErrorStatus.INVALID_INVITATION_CODE));

        return InvitationWithWorkspaceInfoResponse.builder()
                .workspaceId(String.valueOf(workspace.getId()))
                .title(workspace.getTitle())
                .intro(workspace.getDescription())
                .imageUri(workspace.getImageUrl())
                .build();
    }

}
