package com.example.todo_Backend.goal.dto;


import com.example.todo_Backend.goal.entity.IndividualGoal;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IndividualGoalDto {
    private Long id;
    private String title;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public IndividualGoalDto(IndividualGoal goal) {
        this.id = goal.getId();
        this.title = goal.getTitle();
        this.userId = goal.getUserId();
        this.createdAt = goal.getCreatedAt();
        this.updatedAt = goal.getUpdatedAt();
    }
}
