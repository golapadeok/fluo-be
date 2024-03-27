package com.golapadeok.fluo.domain.invitation.exception;

import lombok.Getter;

@Getter
public class InvitationException extends RuntimeException{

    private final InvitationErrorStatus invitationErrorStatus;

    public InvitationException(InvitationErrorStatus invitationErrorStatus) {
        this.invitationErrorStatus = invitationErrorStatus;
    }

}
