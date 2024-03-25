package com.golapadeok.fluo.domain.task.repository;

import com.golapadeok.fluo.domain.task.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("select t from Task t where t.state.id = :stateId")
    List<Task> findByStateId(@Param("stateId") long stateId);
}
