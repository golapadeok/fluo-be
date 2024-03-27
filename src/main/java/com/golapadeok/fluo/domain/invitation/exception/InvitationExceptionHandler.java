package com.golapadeok.fluo.domain.invitation.exception;

import com.golapadeok.fluo.domain.invitation.api.MemberInvitationController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = MemberInvitationController.class)
public class InvitationExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> invitationExceptionHanlder(InvitationException e) {
        return ResponseEntity.badRequest()
                .body(new ErrorResponse(e.getInvitationErrorStatus().getMessage()));
    }

}
