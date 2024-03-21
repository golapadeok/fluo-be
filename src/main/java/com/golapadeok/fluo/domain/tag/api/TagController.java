package com.golapadeok.fluo.domain.tag.api;

import com.golapadeok.fluo.domain.tag.dto.request.TagCreateRequest;
import com.golapadeok.fluo.domain.tag.service.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<Object> createTags(
            @Valid @RequestBody TagCreateRequest request
    ) {
        return ResponseEntity.ok(tagService.createTags(request));
    }

    @DeleteMapping
    public ResponseEntity<Object> deleteTags() {
        return ResponseEntity.ok(null);
    }
}
