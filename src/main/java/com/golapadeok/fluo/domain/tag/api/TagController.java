package com.golapadeok.fluo.domain.tag.api;

import com.golapadeok.fluo.domain.tag.dto.request.TagCreateRequest;
import com.golapadeok.fluo.domain.tag.dto.response.TagCreateResponse;
import com.golapadeok.fluo.domain.tag.dto.response.TagDeleteResponse;
import com.golapadeok.fluo.domain.tag.dto.response.TagSearchResponse;
import com.golapadeok.fluo.domain.tag.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
@Tag(name = "태그 API", description = "태그 관련 API 목록입니다.")
public class TagController {
    private final TagService tagService;

    @GetMapping("/{tagId}")
    @Operation(summary = "태그 단일조회 API", description = "태그 아이디로 조회합니다.")
    public ResponseEntity<TagSearchResponse> searchTags(
            @PathVariable("tagId") @Parameter(description = "조회할 태그 아이디", example = "1") Integer tagId
    ) {
        return ResponseEntity.ok(tagService.searchTags(tagId));
    }

    @PostMapping
    @Operation(summary = "태그 생성 API", description = "워크스페이스 아이디로 태그를 생성합니다.")
    public ResponseEntity<TagCreateResponse> createTags(
            @Valid @RequestBody TagCreateRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(tagService.createTags(request));
    }

    @DeleteMapping("/{tagId}")
    @Operation(summary = "태그 삭제 API", description = "해당 태그 아이디로 삭제합니다.")
    public ResponseEntity<TagDeleteResponse> deleteTags(
            @PathVariable("tagId") @Parameter(description = "삭제할 태그 아이디", example = "1") Integer tagId
    ) {
        return ResponseEntity.ok(tagService.deleteTags(tagId));
    }
}
