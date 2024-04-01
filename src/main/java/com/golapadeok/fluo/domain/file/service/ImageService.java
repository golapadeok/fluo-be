package com.golapadeok.fluo.domain.file.service;

import com.golapadeok.fluo.domain.file.domain.DefaultImage;
import com.golapadeok.fluo.domain.file.repository.DefaultImageRepository;
import com.golapadeok.fluo.domain.file.util.AmazonS3Uploader;
import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import com.golapadeok.fluo.domain.workspace.exception.NotFoundWorkspaceException;
import com.golapadeok.fluo.domain.workspace.repository.WorkspaceRepository;
import io.jsonwebtoken.lang.Assert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final AmazonS3Uploader amazonS3Uploader;
    private final DefaultImageRepository repository;
    private final WorkspaceRepository workspaceRepository;

    private static final String defaultImageUrl = "https://fluo-bucket.s3.ap-northeast-2.amazonaws.com/images/default_ea81d464-2433-4ca2-9281-691187752a05_S88da226f2926405ba7abe131b6e55a90w.png";

    @Transactional
    public String updateImages(MultipartFile multipartFile, Integer workspaceId) {
        //워크스페이스 조회
        Workspace workspace = getWorkspace(workspaceId);
        Assert.notNull(workspace.getImageUrl(), "workspace image url must not null");


        //워크스페이스 새로운 이미지 업로드, 적용
        String imageUrl = amazonS3Uploader.upload(multipartFile);
        workspace.changeImageUrl(imageUrl);

        //디비에 저장
        updateDefaultImage(workspaceId, imageUrl, workspace);
        return imageUrl;
    }


    @Transactional
    public String deleteImages(Integer workspaceId) {
        Workspace workspace = getWorkspace(workspaceId);
        Assert.notNull(workspace.getImageUrl(), "workspace image url must not null");

        //이전 이미지 삭제
        deleteOldImage(workspace.getImageUrl());
        updateDefaultImage(workspaceId, defaultImageUrl, workspace);
        workspace.changeImageUrl(defaultImageUrl);
        return defaultImageUrl;
    }

    private void updateDefaultImage(Integer workspaceId, String imageUrl, Workspace workspace) {
        DefaultImage defaultImage = repository.findByWorkspaceId(workspaceId.longValue())
                .orElseGet(() -> new DefaultImage(imageUrl));
        defaultImage.changeUrl(imageUrl);
        defaultImage.changeWorkspace(workspace);
        repository.save(defaultImage);
    }

    private Workspace getWorkspace(long workspaceId) {
        return workspaceRepository.findById(workspaceId)
                .orElseThrow(NotFoundWorkspaceException::new);
    }

    private void deleteOldImage(String oldUrl) {
        if (!oldUrl.equals(defaultImageUrl)) {
            String fileName = defaultImageUrl.split("/")[3];
            Mono.fromRunnable(() -> amazonS3Uploader.removeFile(fileName))
                    .subscribe();
        }
    }
}
