package com.golapadeok.fluo.domain.task.repository;

import com.golapadeok.fluo.domain.task.domain.ManagerTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ManagerTaskRepository extends JpaRepository<ManagerTask, Long> {
    @Query("select m from ManagerTask m where m.task.id = :taskId")
    List<ManagerTask> findAllByTaskId(@Param("taskId") long taskId);
}
