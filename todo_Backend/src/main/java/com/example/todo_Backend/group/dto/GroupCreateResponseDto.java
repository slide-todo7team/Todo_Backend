package com.example.todo_Backend.group.dto;

import com.example.todo_Backend.group.entity.Group;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GroupCreateResponseDto {
    private Long id;
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public GroupCreateResponseDto(Group group){
        this.id = group.getId();
        this.title = group.getTitle();
        this.createdAt = group.getCreatedAt();
        this.updatedAt = group.getUpdatedAt();

    }

}
