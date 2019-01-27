package com.mmatuszewski.todo.domain.repository;

import com.mmatuszewski.todo.domain.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Long> {
    List<Todo> findByIdAndStatus(Long id, String status);
}
