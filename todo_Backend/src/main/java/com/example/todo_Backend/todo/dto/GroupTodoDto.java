package com.example.todo_Backend.todo.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupTodoDto {
    private Long id;
    private String title;
    private Integer done;
    private Long noteId;
}