package com.example.todo_Backend.group.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupCodeDto {
    @NotNull
    private String secretCode;
}
