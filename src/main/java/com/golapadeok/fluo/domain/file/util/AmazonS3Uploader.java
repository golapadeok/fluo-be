package com.golapadeok.fluo.domain.file.util;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import io.jsonwebtoken.lang.Assert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class AmazonS3Uploader {
    @Value("${file.path.image}")
    private String defaultImagePath;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3Client;

    public String upload(MultipartFile multipartFile) {
        final String fileName = defaultImagePath + "_" + UUID.randomUUID() + "_" + multipartFile.getOriginalFilename();
        final File convertFile = localUpload(multipartFile);
        final String s3Url = s3Upload(convertFile, fileName);

        removeNewFile(convertFile);
        return s3Url;
    }

    private File localUpload(MultipartFile multipartFile) {
        return convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 전환 실패"));
    }

    private String s3Upload(File uploadFile, String fileName) {
        return putS3(uploadFile, fileName);
    }

    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(
                new PutObjectRequest(bucket, fileName, uploadFile)
                        .withCannedAcl(CannedAccessControlList.PublicRead)
        );
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        } else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }

    private Optional<File> convert(MultipartFile file) {

        Assert.notNull(file.getOriginalFilename(), "file original name must not null");
        File convertFile = new File(file.getOriginalFilename());

        try {
            if (convertFile.createNewFile()) {
                try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                    fos.write(file.getBytes());
                }
                return Optional.of(convertFile);
            }
        } catch (IOException e) {
            log.error("이미지 쓰기 작업에 실패했습니다.");
        }


        return Optional.empty();
    }
}
