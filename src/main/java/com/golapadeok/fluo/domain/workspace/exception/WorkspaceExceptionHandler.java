package com.golapadeok.fluo.domain.workspace.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class WorkspaceExceptionHandler {

    @ExceptionHandler(NotFoundWorkspaceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handlerNotFoundWorkspaceException(NotFoundWorkspaceException e) {
        return ResponseEntity.badRequest().body(
                new ErrorResponse(e.getWorkspaceStatus().getMessage())
        );
    }


}
