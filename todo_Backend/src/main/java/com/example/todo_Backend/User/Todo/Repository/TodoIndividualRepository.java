package com.example.todo_Backend.User.Todo.Repository;

import com.example.todo_Backend.User.Todo.Entity.IndividualModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoIndividualRepository extends JpaRepository<IndividualModel,Long> {
    void deleteAllById(Long memberId, int goalId, int todoId);



}
