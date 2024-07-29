package com.example.todo_Backend.group.repository;

import com.example.todo_Backend.group.dto.GroupDto;
import com.example.todo_Backend.group.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {

    Optional<Group> findByTitle(String title);
    Optional<Group> findBySecretCode(String secretCode);

//    List<Group> findByGroupUserMemberId(Long memberId);
}

