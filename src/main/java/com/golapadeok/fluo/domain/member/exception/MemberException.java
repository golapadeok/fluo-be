package com.golapadeok.fluo.domain.member.exception;

import lombok.Getter;

@Getter
public class MemberException extends RuntimeException{

    private MemberErrorStatus memberErrorStatus;

    public MemberException(MemberErrorStatus memberErrorStatus) {
        this.memberErrorStatus = memberErrorStatus;
    }

}
