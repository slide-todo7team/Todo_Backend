package com.example.todo_Backend.goal.repository;

import com.example.todo_Backend.goal.entity.IndividualGoal;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IndividualGoalRepository extends JpaRepository<IndividualGoal,Long> {
    List<IndividualGoal> findAllByUserId(Long userId);

    List<IndividualGoal> findAllByUserIdAndIdGreaterThan(Long userId, Long cursor, Pageable pageable);
    Long countByUserId(Long userId);
}
