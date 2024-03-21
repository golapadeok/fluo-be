package com.golapadeok.fluo.domain.tag.api;

import com.golapadeok.fluo.domain.tag.dto.request.TagCreateRequest;
import com.golapadeok.fluo.domain.tag.dto.response.TagCreateResponse;
import com.golapadeok.fluo.domain.tag.dto.response.TagDeleteResponse;
import com.golapadeok.fluo.domain.tag.service.TagService;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;

    @GetMapping
    public ResponseEntity<Object> searchTags() {
        return ResponseEntity.ok(null);
    }

    @PostMapping
    public ResponseEntity<TagCreateResponse> createTags(
            @Valid @RequestBody TagCreateRequest request
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(tagService.createTags(request));
    }

    @DeleteMapping("/{tagId}")
    public ResponseEntity<TagDeleteResponse> deleteTags(
            @PathVariable("tagId") @Parameter(description = "삭제할 태그 아이디") Integer tagId
    ) {
        return ResponseEntity.ok(tagService.deleteTags(tagId));
    }
}
