package com.golapadeok.fluo.domain.member.dto.response;

import com.golapadeok.fluo.domain.member.domain.Invitation;
import lombok.*;

@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InvitationWorkspaceInfo {

    private String invitationId;
    private WorkspaceInfo workspace;
    private boolean isPending;

    @Builder
    public InvitationWorkspaceInfo(String invitationId, WorkspaceInfo workspace, boolean isPending) {
        this.invitationId = invitationId;
        this.workspace = workspace;
        this.isPending = isPending;
    }

    public static InvitationWorkspaceInfo of(Invitation invitation) {
        return InvitationWorkspaceInfo.builder()
                .invitationId(String.valueOf(invitation.getId()))
                .workspace(WorkspaceInfo.of(invitation.getWorkspace()))
//                .isPending(invitation.getIsPending())
                .build();
    }
}
