package com.golapadeok.fluo.domain.role.api;

import com.golapadeok.fluo.common.security.domain.PrincipalDetails;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestController {

    @PostMapping("/user")
    public ResponseEntity user(@AuthenticationPrincipal PrincipalDetails principalDetails, HttpServletRequest request) {
        log.info("PrincipalDetails({}) invoked.", principalDetails.getMember().getName());
        log.info("access token : {}", request.getHeader(HttpHeaders.AUTHORIZATION));
        return new ResponseEntity(HttpStatus.OK);
    }

}
