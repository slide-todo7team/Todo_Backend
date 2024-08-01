package com.example.todo_Backend.goal.dto;

import lombok.*;

import java.util.List;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GroupProgressDto {
    private Integer completedPercent;
    private List<GroupGoalMemDto> memebers;

    @Builder
    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GroupGoalMemDto {
        private String name;
        private String color;
        private Integer contributionPercent;
    }

}
