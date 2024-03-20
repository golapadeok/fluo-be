package com.golapadeok.fluo.domain.role.repository;

import com.golapadeok.fluo.domain.role.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<List<Role>> findByWorkspaceId(Long workspaceId);

    Optional<Role> findByNameAndWorkspaceId(String name, Long workspaceId);

}
