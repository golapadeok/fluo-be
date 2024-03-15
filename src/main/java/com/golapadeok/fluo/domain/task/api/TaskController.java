package com.golapadeok.fluo.domain.task.api;

import com.golapadeok.fluo.domain.task.dto.TaskDto;
import com.golapadeok.fluo.domain.task.dto.TaskIdDto;
import com.golapadeok.fluo.domain.task.dto.request.TaskRequest;
import com.golapadeok.fluo.domain.task.service.TaskService;
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
    private final TaskService taskService;

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDto> getTask(
            @PathVariable("taskId") Integer taskId
    ) {
        TaskDto task = taskService.getTask(taskId);
        return ResponseEntity.ok(task);
    }

    @PostMapping
    @Operation(summary = "업무 생성 API", description = "새로운 업무를 생성합니다.")
    public ResponseEntity<TaskDto> createTask(
            @Valid @RequestBody TaskRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(taskService.createTask(request));
    }

    @PutMapping("/{taskId}")
    @Operation(summary = "업무 수정 API", description = "해당 업무를 수정합니다.")
    public ResponseEntity<TaskDto> updateTask(
            @PathVariable("taskId") Integer taskId,
            @Valid @RequestBody TaskRequest request
    ) {
        TaskDto taskDto = taskService.updateTask(taskId, request);
        return ResponseEntity.ok(taskDto);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<TaskIdDto> deleteTask(
            @PathVariable("taskId") Integer taskId
    ) {
        TaskIdDto taskIdDto = taskService.deleteTask(taskId);
        return ResponseEntity.ok(taskIdDto);
    }
}
