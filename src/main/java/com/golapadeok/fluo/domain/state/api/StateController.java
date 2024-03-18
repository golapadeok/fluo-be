package com.golapadeok.fluo.domain.state.api;


import com.golapadeok.fluo.domain.state.dto.request.StateCreateRequest;
import com.golapadeok.fluo.domain.state.dto.request.StateUpdateRequest;
import com.golapadeok.fluo.domain.state.dto.response.StateCreateResponse;
import com.golapadeok.fluo.domain.state.dto.response.StateSearchResponse;
import com.golapadeok.fluo.domain.state.dto.response.StateUpdateResponse;
import com.golapadeok.fluo.domain.state.service.StateCreateService;
import com.golapadeok.fluo.domain.state.service.StateSearchService;
import com.golapadeok.fluo.domain.state.service.StateUpdateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@Tag(name = "상태 API", description = "상태 관련 API 목록")
@RequestMapping("/states")
public class StateController {
    private final StateCreateService stateCreateService;
    private final StateSearchService stateSearchService;
    private final StateUpdateService stateUpdateService;

    @GetMapping("/{stateId}")
    @Operation(summary = "상태 단일조회 API", description = "해당 상태를 조회합니다.")
    public ResponseEntity<StateSearchResponse> createState(
            @PathVariable(name = "stateId") @Parameter(description = "조회할 상태 아이디") Integer stateId
    ) {
        return ResponseEntity.ok(stateSearchService.search(stateId));
    }

    @PostMapping
    @Operation(summary = "상태 생성 API", description = "입력받은 워크스페이스에 상태를 추가합니다.")
    public ResponseEntity<StateCreateResponse> createState(
            @Valid @RequestBody StateCreateRequest request
    ) {
        return ResponseEntity
                .created(URI.create("/states/1"))
                .body(stateCreateService.create(request));
    }

    @PutMapping("/{stateId}")
    @Operation(summary = "상태 수정 API", description = "상태를 수정합니다.")
    public ResponseEntity<StateUpdateResponse> createState(
            @PathVariable(name = "stateId") @Parameter(description = "조회할 상태 아이디") Integer stateId,
            @Valid @RequestBody StateUpdateRequest request
    ) {
        return ResponseEntity.ok(stateUpdateService.update(stateId, request));
    }
}
