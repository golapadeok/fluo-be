package com.golapadeok.fluo.domain.member.repository;

import com.golapadeok.fluo.domain.member.domain.WorkspaceMember;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WorkspaceMemberRepository extends JpaRepository<WorkspaceMember, Long> {

    // 커서 기반 페이징 = 커서id가 null일때
    Page<WorkspaceMember> findByMemberIdOrderByIdDesc(Long memberId, Pageable pageable);

    // 커서 기반 페이징 = 커서id가 null이 아닐 때
    Page<WorkspaceMember> findByIdLessThanAndMemberIdOrderByIdDesc(Long id, Long memberId, Pageable pageable);

    // 워크스페이스 정보 조회
    Optional<List<WorkspaceMember>> findByWorkspaceId(Long workspaceId);

    Optional<WorkspaceMember> findByMemberIdAndWorkspaceId(Long memberId, Long workspaceId);

    Long countByMemberId(Long memberId);
}
