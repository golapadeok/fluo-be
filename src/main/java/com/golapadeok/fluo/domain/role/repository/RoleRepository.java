package com.golapadeok.fluo.domain.role.repository;

import com.golapadeok.fluo.domain.role.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<List<Role>> findByWorkspaceId(Long workspaceId);

    Optional<Role> findByNameAndWorkspaceId(String name, Long workspaceId);

    Optional<Role> findByNameAndWorkspaceIdAndIdIsNot(String name, Long workspaceId, Long id);

    @Query("select r from Role r where r.name = '일반' and r.workspace.id = :workspaceId")
    Optional<Role> findByNameAndWorkspaceId(@Param("workspaceId") Long workspaceId);

}
