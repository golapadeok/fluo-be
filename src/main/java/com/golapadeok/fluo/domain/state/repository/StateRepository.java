package com.golapadeok.fluo.domain.state.repository;

import com.golapadeok.fluo.domain.state.domain.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StateRepository extends JpaRepository<State, Long> {
    Optional<State> findByIdAndWorkspaceId(long id, long workspaceId);
}
