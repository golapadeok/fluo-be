package com.golapadeok.fluo.domain.member.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InvitationWithWorkspaceInfoResponse {

    private String workspaceId;
    private String title;
    private String intro;

    @Builder
    public InvitationWithWorkspaceInfoResponse(String workspaceId, String title, String intro) {
        this.workspaceId = workspaceId;
        this.title = title;
        this.intro = intro;
    }
}
