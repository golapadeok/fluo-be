package com.golapadeok.fluo.domain.role.repository;

import com.golapadeok.fluo.domain.role.domain.MemberRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRoleRepository extends JpaRepository<MemberRole, Long> {
}
