package com.takus.toDoApp.repository;

import com.takus.toDoApp.model.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TaskRepository extends JpaRepository<TaskEntity,Long> {
    List<TaskEntity> findByUserId(Long userId);
}
