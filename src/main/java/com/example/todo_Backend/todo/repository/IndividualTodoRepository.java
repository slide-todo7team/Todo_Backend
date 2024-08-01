package com.example.todo_Backend.todo.repository;

import com.example.todo_Backend.todo.entity.IndividualTodo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IndividualTodoRepository extends JpaRepository<IndividualTodo, Long> {
    List<IndividualTodo> findAllByGoalId(Long id);
}
