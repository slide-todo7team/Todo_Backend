package com.example.todo_Backend.User.Todo.Repository;

import com.example.todo_Backend.User.Todo.Entity.GroupModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TodoGroupRepository extends JpaRepository<GroupModel,Long> {


    @Query("SELECT t FROM grouptodos t WHERE t.member_id=memberId")
    List<GroupModel> findAllByMemberId(@Param("memberId") Long memberId);
}
