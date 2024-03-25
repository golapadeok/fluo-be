package com.golapadeok.fluo.domain.state.repository;

import com.golapadeok.fluo.domain.state.domain.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface StateRepository extends JpaRepository<State, Long> {
    Optional<State> findByIdAndWorkspaceId(long id, long workspaceId);

    @Query("select s from State s where s.isDefault = :isDefault")
    Optional<State> findByIsDefault(@Param("isDefault") boolean isDefault);
}
