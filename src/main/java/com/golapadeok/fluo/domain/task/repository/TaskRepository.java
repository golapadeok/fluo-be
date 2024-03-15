package com.golapadeok.fluo.domain.task.repository;

import com.golapadeok.fluo.domain.task.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
