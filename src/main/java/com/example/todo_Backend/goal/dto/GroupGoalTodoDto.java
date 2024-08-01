package com.example.todo_Backend.goal.dto;

import com.example.todo_Backend.todo.dto.GroupTodoDto;
import com.example.todo_Backend.todo.entity.GroupTodo;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupGoalTodoDto {
    private String title;
    private Long id;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private GroupProgressDto progress;
    private List<GroupTodoDto> todos;


    @Getter
    @Setter
    @Builder
    public static class GroupTodoDto {
        private Long noteId; //연결된 노트 Id
        private Long id; //할일 id
        private String title;
        private Integer done;
    }


}
