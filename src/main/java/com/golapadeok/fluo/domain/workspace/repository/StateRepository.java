package com.golapadeok.fluo.domain.workspace.repository;

import com.golapadeok.fluo.domain.workspace.domain.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<State, Long> {
}
