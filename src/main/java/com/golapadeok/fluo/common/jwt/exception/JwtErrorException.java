package com.golapadeok.fluo.common.jwt.exception;

import com.golapadeok.fluo.common.jwt.JwtErrorStatus;
import lombok.Getter;

@Getter
public class JwtErrorException extends RuntimeException{

    private JwtErrorStatus jwtErrorStatus;

    public JwtErrorException(JwtErrorStatus jwtErrorStatus) {
        this.jwtErrorStatus = jwtErrorStatus;
    }

}
