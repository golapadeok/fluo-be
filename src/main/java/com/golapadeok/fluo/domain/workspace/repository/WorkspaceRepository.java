package com.golapadeok.fluo.domain.workspace.repository;

import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {
}
