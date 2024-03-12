package com.golapadeok.fluo.domain.role.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @PostMapping("/user")
    public ResponseEntity user() {
        return new ResponseEntity(HttpStatus.OK);
    }

}
