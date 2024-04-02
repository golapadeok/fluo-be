package com.golapadeok.fluo.domain.social.exception;

import com.golapadeok.fluo.domain.social.api.SocialController;
import com.golapadeok.fluo.domain.task.exception.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = SocialController.class)
public class SocialExceptionHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity<ErrorResponse> socialExceptionHandler(SocialErrorException e) {
        return ResponseEntity.status(e.getSocialErrorStatus().getStatus()).body(
                new ErrorResponse(e.getSocialErrorStatus().getMessage())
        );
    }

}
