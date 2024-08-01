package com.example.todo_Backend.goal.dto;

import lombok.*;

import java.util.List;

@Builder
@Setter
@Getter
@NoArgsConstructor
public class CursorResponseDto<T> {
    private Long nextCursor;
    private Long totalCount;
    private List<T> individualGoalDtos;

    public CursorResponseDto(Long nextCursor, Long totalCount, List<T> individualGoalDtos) {
        this.nextCursor = nextCursor;
        this.totalCount = totalCount;
        this.individualGoalDtos = individualGoalDtos;
    }
}
