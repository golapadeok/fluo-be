package com.golapadeok.fluo.domain.task.exception;

import com.golapadeok.fluo.domain.task.api.TaskController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = TaskController.class)
public class TaskExceptionHandler {

    @ExceptionHandler(NotFoundTaskException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handlerNotFoundTaskException(NotFoundTaskException e) {
        return ResponseEntity.badRequest().body(
                new ErrorResponse(e.getTaskStatus().getMessage())
        );
    }


}
