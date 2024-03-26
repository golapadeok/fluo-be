package com.golapadeok.fluo.domain.invitation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@Builder
@AllArgsConstructor
public class MemberInvitationListResponse {

    private String total;
    private String cursorId;
    private List<InvitationWorkspaceInfo> items;

}