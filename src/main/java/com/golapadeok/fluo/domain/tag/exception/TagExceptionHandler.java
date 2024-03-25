package com.golapadeok.fluo.domain.tag.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TagExceptionHandler {

    @ExceptionHandler(NotFoundTagException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handlerNotFoundTagException(NotFoundTagException e) {
        return ResponseEntity.badRequest().body(
                new ErrorResponse(e.getTagStatus().getMessage())
        );
    }


}
