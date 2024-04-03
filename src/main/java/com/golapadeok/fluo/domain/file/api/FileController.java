package com.golapadeok.fluo.domain.file.api;

import com.golapadeok.fluo.domain.file.service.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController {

    private final ImageService imageService;

    @PostMapping(value = "/images/{workspaceId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> updateImage(
            @RequestPart("image") MultipartFile fileRequest,
            @PathVariable("workspaceId") Integer workspaceId
    ) {
        String url = imageService.updateImages(fileRequest, workspaceId);
        return ResponseEntity.created(URI.create(url)).body(url);
    }

    @DeleteMapping(value = "/images/{workspaceId}")
    public ResponseEntity<Object> deleteImage(
            @PathVariable("workspaceId") Integer workspaceId
    ) {
        String url = imageService.deleteImages(workspaceId);
        return ResponseEntity.created(URI.create(url)).body(url);
    }
}
