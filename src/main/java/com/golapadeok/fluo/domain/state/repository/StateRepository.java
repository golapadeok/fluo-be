package com.golapadeok.fluo.domain.state.repository;

import com.golapadeok.fluo.domain.state.domain.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<State, Long> {
}
