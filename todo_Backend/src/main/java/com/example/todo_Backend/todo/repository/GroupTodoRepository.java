package com.example.todo_Backend.todo.repository;

import com.example.todo_Backend.todo.entity.GroupTodo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupTodoRepository extends JpaRepository<GroupTodo, Long> {
    List<GroupTodo> findAllByGoalId(Long id);
}
