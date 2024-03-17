package com.golapadeok.fluo.domain.state;

import com.golapadeok.fluo.domain.state.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<State, Long> {
}
