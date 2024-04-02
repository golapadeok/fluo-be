package com.golapadeok.fluo.domain.invitation.dto;

import lombok.Getter;

@Getter
public class InvitationCodeDto {
    private final String invitationCode;

    public InvitationCodeDto(String invitationCode) {
        this.invitationCode = invitationCode;
    }
}
