package com.example.todo_Backend.group.repository;

import com.example.todo_Backend.group.entity.GroupUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GroupUserRepository extends JpaRepository<GroupUser, Long> {
    List<GroupUser> findByMemberId(Long memberId);

    void deleteByGroupId(Long groupId);

    void deleteByGroupIdAndMemberId(Long groupId, Long memberId);

    List<GroupUser> findAllByGroupId(Long groupId);
}
