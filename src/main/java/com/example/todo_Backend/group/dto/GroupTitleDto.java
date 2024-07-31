package com.example.todo_Backend.group.dto;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupTitleDto {
    @NotNull
    private String title;
}
