package com.example.todo_Backend.goal.dto;

import jdk.jshell.Snippet;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IndividualGoalTodoDto {
    private Long id; //목표 id
    private String title;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer progress;
    private List<IndividualTodoDto> todos;

    @Getter
    @Setter
    @Builder
    public static class IndividualTodoDto {
        private Long noteId; //연결된 노트 Id
        private Long id; //할일 id
        private String title;
        private Integer done;

    }

}
