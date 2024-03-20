package com.golapadeok.fluo.domain.file.service;

import com.golapadeok.fluo.domain.file.domain.DefaultImage;
import com.golapadeok.fluo.domain.file.repository.DefaultImageRepository;
import com.golapadeok.fluo.domain.file.util.AmazonS3Uploader;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import com.golapadeok.fluo.domain.workspace.exception.NotFoundWorkspaceException;
import com.golapadeok.fluo.domain.workspace.repository.WorkspaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
// TODO KDY 코드 변환 필요
public class ImageService {
    private final AmazonS3Uploader amazonS3Uploader;
    private final DefaultImageRepository repository;
    private final WorkspaceRepository workspaceRepository;


    @Transactional
    public String createImage(MultipartFile multipartFile, Integer workspaceId) throws IOException {
        final long id = workspaceId;
        Workspace workspace = workspaceRepository.findById(id)
                .orElseThrow(NotFoundWorkspaceException::new);

        String s3Path = amazonS3Uploader.localUpload(multipartFile);
        DefaultImage defaultImage = new DefaultImage(s3Path);
        defaultImage.changeWorkspace(workspace);
        repository.save(defaultImage);

        return s3Path;
    }

    @Transactional(readOnly = true)
    public List<String> searchImages() {
        List<DefaultImage> defaultImages = repository.findAll();

        return defaultImages.stream()
                .map(DefaultImage::getUrl)
                .toList();
    }
}
