package com.golapadeok.fluo.domain.role.exception;

import com.golapadeok.fluo.domain.role.api.RoleController;
import com.golapadeok.fluo.domain.social.exception.SocialErrorException;
import com.golapadeok.fluo.domain.task.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = RoleController.class)
public class RoleExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity<ErrorResponse> roleExceptionHandler(RoleException e) {
        return ResponseEntity
                .status(e.getRoleErrorStatus().getStatus())
                .body(new ErrorResponse(e.getRoleErrorStatus().getMessage()));
    }


}
