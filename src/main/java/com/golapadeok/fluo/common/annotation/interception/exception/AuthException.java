package com.golapadeok.fluo.common.annotation.interception.exception;

import lombok.Getter;

@Getter
public class AuthException extends RuntimeException{

    private final AuthStatus authStatus;

    public AuthException(AuthStatus authStatus) {
        this.authStatus = authStatus;
    }

}
