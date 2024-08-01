package com.example.todo_Backend.goal.repository;

import com.example.todo_Backend.goal.dto.GroupGoalDto;
import com.example.todo_Backend.goal.entity.GroupGoal;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupGoalRepository extends JpaRepository<GroupGoal,Long> {
    List<GroupGoal> findByGroupId(Long groupId);

    Optional<GroupGoal> findByIdAndGroupId(Long goalId, Long groupId);

    List<GroupGoal> findAllByGroupId(Long groupId);

    Long countByGroupId(Long groupId);

    List<GroupGoal> findAllByGroupIdAndIdGreaterThan(Long groupId, Long cursor, PageRequest of);

    List<GroupGoal> findAllByGroupId(Long groupId, PageRequest of);
}
