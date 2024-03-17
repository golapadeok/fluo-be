package com.golapadeok.fluo.domain.task.api;

import com.golapadeok.fluo.domain.task.dto.request.TaskCreateRequest;
import com.golapadeok.fluo.domain.task.service.TaskCreateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "업무 API", description = "업무 관련 API 목록")
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskCreateService taskCreateService;

    @GetMapping("/{taskId}")
    public ResponseEntity<Object> getTask(
            @PathVariable("taskId") Integer taskId
    ) {
        return ResponseEntity.ok(null);
    }

    @PostMapping
    @Operation(summary = "업무 생성 API", description = "새로운 업무를 생성합니다.")
    public ResponseEntity<Object> createTask(
            @Valid @RequestBody TaskCreateRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(taskCreateService.createTask(request));
    }

    @PutMapping("/{taskId}")
    @Operation(summary = "업무 수정 API", description = "해당 업무를 수정합니다.")
    public ResponseEntity<Object> updateTask(
            @PathVariable("taskId") Integer taskId) {
        return ResponseEntity.ok(null);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Object> deleteTask(
            @PathVariable("taskId") Integer taskId) {
        return ResponseEntity.ok(null);
    }
}
