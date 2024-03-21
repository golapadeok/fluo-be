package com.golapadeok.fluo.domain.member.repository;

import com.golapadeok.fluo.domain.member.domain.WorkspaceMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WorkspaceMemberRepository extends JpaRepository<WorkspaceMember, Long> {

    Optional<List<WorkspaceMember>> findByWorkspaceId(Long workspaceId);
}
