package com.golapadeok.fluo.domain.workspace.repository;

import com.golapadeok.fluo.domain.workspace.domain.Workspace;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {

    Optional<Workspace> findByInvitationsCode(String invitationsCode);

}
