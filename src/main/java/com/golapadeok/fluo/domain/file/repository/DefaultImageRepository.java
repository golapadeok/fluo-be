package com.golapadeok.fluo.domain.file.repository;

import com.golapadeok.fluo.domain.file.domain.DefaultImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DefaultImageRepository extends JpaRepository<DefaultImage, Long> {
    Optional<DefaultImage> findByWorkspaceId(Long workspaceId);
}
