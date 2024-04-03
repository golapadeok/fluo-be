package com.golapadeok.fluo.domain.invitation.dto.response;

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
    private String imageUri;

    @Builder
    public InvitationWithWorkspaceInfoResponse(String workspaceId, String title, String intro, String imageUri) {
        this.workspaceId = workspaceId;
        this.title = title;
        this.intro = intro;
        this.imageUri = imageUri;
    }
}
