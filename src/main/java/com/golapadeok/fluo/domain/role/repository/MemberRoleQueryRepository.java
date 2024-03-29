package com.golapadeok.fluo.domain.role.repository;

import com.golapadeok.fluo.domain.role.domain.MemberRole;

import java.util.Optional;

public interface MemberRoleQueryRepository {

    Optional<MemberRole> findWorkspaceWithMemberRoleList(Long memberId, Long workspaceId);
}
