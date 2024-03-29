package com.golapadeok.fluo.domain.member.exception;

import lombok.Getter;

@Getter
public enum MemberErrorStatus {

    NOT_FOUND_MEMBER(400, "존재하지 않는 회원입니다."),
    NOT_FOUND_WORKSPACE(400, "존재하지 않는 워크스페이스입니다.");

    private final int status;
    private final String message;

    MemberErrorStatus(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
