package com.example.todo_Backend.goal.dto;

import com.example.todo_Backend.goal.entity.GroupGoal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupGoalDto {
    private Long id;
    private String title;
    private Long groupId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public GroupGoalDto(GroupGoal saveGroupGoal) {
        this.id = saveGroupGoal.getId();
        this.title = saveGroupGoal.getTitle();
        this.groupId = saveGroupGoal.getGroupId();
        this.createdAt = saveGroupGoal.getCreatedAt();
        this.updatedAt = saveGroupGoal.getUpdatedAt();
    }

    public GroupGoalDto(String title, Long id, Long groupId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.title = title;
        this.id = id;
        this.groupId = groupId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
