package com.golapadeok.fluo.domain.invitation.exception;

import lombok.Getter;

@Getter
public enum InvitationErrorStatus {

    NOT_FOUND_INVITATION(400, "존재하지 않는 초대 입니다."),
    INVALID_INVITATION_CODE(400, "잘못된 초대코드입니다. 다시 입력해주세요. "),
    NOT_FOUND_MEMBER(400, "존재하지 않는 회원 입니다."),
    NOT_FOUND_WORKSPACE(400, "존재하지 않는 워크스페이스 입니다."),
    NOT_FOUND_ROLE(400, "존재하지 않는 역할 입니다.");

    private final int status;
    private final String message;

    InvitationErrorStatus(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
