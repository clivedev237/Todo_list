package com.takus.toDoApp.repository;

import com.takus.toDoApp.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    boolean existsByName(String name);
}
