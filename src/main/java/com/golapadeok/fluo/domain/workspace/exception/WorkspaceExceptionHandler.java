package com.golapadeok.fluo.domain.workspace.exception;

import com.golapadeok.fluo.domain.workspace.api.WorkspaceController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = WorkspaceController.class)
public class WorkspaceExceptionHandler {

    @ExceptionHandler(NotFoundWorkspaceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handlerNotFoundTaskException(NotFoundWorkspaceException e) {
        return ResponseEntity.badRequest().body(
                new ErrorResponse(e.getWorkspaceStatus().getMessage())
        );
    }


}
