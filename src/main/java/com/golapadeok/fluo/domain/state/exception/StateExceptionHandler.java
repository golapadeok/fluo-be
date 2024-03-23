package com.golapadeok.fluo.domain.state.exception;

import com.golapadeok.fluo.domain.task.exception.ErrorResponse;
import com.golapadeok.fluo.domain.task.exception.NotFoundTaskException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class StateExceptionHandler {

    @ExceptionHandler(NotFoundStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handlerNotFoundStateException(NotFoundStateException e) {
        return ResponseEntity.badRequest().body(
                new ErrorResponse(e.getStateStatus().getMessage())
        );
    }


}
