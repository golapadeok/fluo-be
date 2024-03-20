package com.golapadeok.fluo.domain.file.api;

import com.golapadeok.fluo.domain.file.repository.DefaultImageRepository;
import com.golapadeok.fluo.domain.file.service.ImageService;
import com.golapadeok.fluo.domain.file.util.AmazonS3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URI;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/files")
public class FileController {

    private final AmazonS3Uploader amazonS3Uploader;

    private final ImageService imageService;


    @PostMapping(value = "/images/{workspaceId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Object> createImage(
            @RequestPart("fileRequest") MultipartFile fileRequest,
            @PathVariable("workspaceId") Integer workspaceId
    ) throws IOException {
        String url = imageService.createImage(fileRequest, workspaceId);
        return ResponseEntity.created(URI.create(url)).body(url);
    }

    @GetMapping("/images")
    public ResponseEntity<Object> searchImage() {
        return ResponseEntity.ok(imageService.searchImages());
    }


}
