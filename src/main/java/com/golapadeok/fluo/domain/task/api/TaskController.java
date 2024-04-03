package com.golapadeok.fluo.domain.task.api;

import com.golapadeok.fluo.common.annotation.AuthCheck;
import com.golapadeok.fluo.domain.role.domain.Credential;
import com.golapadeok.fluo.domain.task.dto.request.TaskCreateRequest;
import com.golapadeok.fluo.domain.task.dto.request.TaskUpdateRequest;
import com.golapadeok.fluo.domain.task.dto.response.TaskDeleteResponse;
import com.golapadeok.fluo.domain.task.dto.response.TaskDetailResponse;
import com.golapadeok.fluo.domain.task.service.TaskCreateService;
import com.golapadeok.fluo.domain.task.service.TaskDeleteService;
import com.golapadeok.fluo.domain.task.service.TaskSearchService;
import com.golapadeok.fluo.domain.task.service.TaskUpdateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "업무 API", description = "업무 관련 API 목록")
@RequestMapping("/tasks")
public class TaskController {

    private final TaskCreateService taskCreateService;
    private final TaskSearchService taskSearchService;
    private final TaskUpdateService taskUpdateService;
    private final TaskDeleteService taskDeleteService;

    @GetMapping("/{taskId}")
    @Operation(summary = "업무 단일 조회 API", description = "해당 업무를 조회합니다.")
    public ResponseEntity<TaskDetailResponse> getTask(
            @PathVariable("taskId") @Parameter(description = "조회할 업무 아이디") Integer taskId
    ) {
        return ResponseEntity.ok(taskSearchService.search(taskId));
    }

//    @AuthCheck(credential = Credential.CREATE_TASK)
    @PostMapping
    @Operation(summary = "업무 생성 API", description = "새로운 업무를 생성합니다.")
    public ResponseEntity<TaskDetailResponse> createTask(
            @Valid @RequestBody TaskCreateRequest request
    ) {
        return ResponseEntity
                .ok(taskCreateService.createTask(request));
    }

//    @AuthCheck(credential = Credential.MODIFY_TASK)
    @PutMapping("/{taskId}")
    @Operation(summary = "업무 수정 API", description = "해당 업무를 수정합니다.")
    public ResponseEntity<TaskDetailResponse> updateTask(
            @PathVariable("taskId") Integer taskId,
            @Valid @RequestBody TaskUpdateRequest request) {
        log.info("updateTask({}, {})", taskId, request.toString());
        return ResponseEntity.ok(taskUpdateService.update(taskId, request));
    }

//    @AuthCheck(credential = Credential.DELETE_TASK)
    @DeleteMapping("/{taskId}")
    public ResponseEntity<TaskDeleteResponse> deleteTask(
            @PathVariable("taskId") Integer taskId) {
        log.info("deleteTask({}) invoked.", taskId);

        return ResponseEntity.ok(taskDeleteService.delete(taskId));
    }
}
