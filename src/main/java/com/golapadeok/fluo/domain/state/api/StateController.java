package com.golapadeok.fluo.domain.state.api;


import com.golapadeok.fluo.domain.state.dto.request.StateCreateRequest;
import com.golapadeok.fluo.domain.state.service.StateCreateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@Tag(name = "상태 API", description = "상태 관련 API 목록")
@RequestMapping("/states")
public class StateController {
    private final StateCreateService stateCreateService;

    @PostMapping
    @Operation(summary = "상태 생성 API", description = "입력받은 워크스페이스에 상태를 추가합니다.")
    public ResponseEntity<Object> createState(
            @Valid @RequestBody StateCreateRequest request
    ) {
        return ResponseEntity
                .created(URI.create("/states/1"))
                .body(stateCreateService.create(request));
    }
}
