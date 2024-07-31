package com.example.todo_Backend.goal.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupGoalTodoResponseDto {
    private String title;
    private List<GroupGoalTodoDto> groupGoals;

}
