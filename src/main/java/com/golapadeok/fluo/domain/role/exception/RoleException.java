package com.golapadeok.fluo.domain.role.exception;

import lombok.Getter;

@Getter
public class RoleException extends RuntimeException{

    private final RoleErrorStatus roleErrorStatus;

    public RoleException(RoleErrorStatus roleErrorStatus) {
        this.roleErrorStatus = roleErrorStatus;
    }

}
