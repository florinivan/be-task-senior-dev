package com.amaris.task.jpa.repository;

import com.amaris.task.jpa.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
