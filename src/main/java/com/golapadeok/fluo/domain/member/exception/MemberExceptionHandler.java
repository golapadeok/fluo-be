package com.golapadeok.fluo.domain.member.exception;

import com.golapadeok.fluo.domain.member.api.MemberController;
import com.golapadeok.fluo.domain.task.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = MemberController.class)
public class MemberExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> memberExceptionHandler(MemberException e) {
        return ResponseEntity
                .status(e.getMemberErrorStatus().getStatus())
                .body(new ErrorResponse(e.getMemberErrorStatus().getMessage()));
    }

}
