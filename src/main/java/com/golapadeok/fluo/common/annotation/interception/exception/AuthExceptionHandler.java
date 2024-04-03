package com.golapadeok.fluo.common.annotation.interception.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> exceptionHandler(AuthException e) {
        return ResponseEntity
                .status(e.getAuthStatus().getStatus())
                .body(new ErrorResponse(e.getAuthStatus().getMessage()));
    }

}
