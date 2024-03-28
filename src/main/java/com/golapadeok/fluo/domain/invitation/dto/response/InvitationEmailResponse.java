package com.golapadeok.fluo.domain.invitation.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InvitationEmailResponse {

    private String invitationId;
    private String workspaceId;
    private MemberInfo member;

    @Builder
    public InvitationEmailResponse(String invitationId, String workspaceId, MemberInfo member) {
        this.invitationId = invitationId;
        this.workspaceId = workspaceId;
        this.member = member;
    }
}
