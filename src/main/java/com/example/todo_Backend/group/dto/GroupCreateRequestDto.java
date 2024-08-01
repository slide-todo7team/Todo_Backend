package com.example.todo_Backend.group.dto;

import lombok.*;
import org.springframework.stereotype.Service;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
public class GroupCreateRequestDto {
    private String title;
    private String createUser;
    private String secretCode;
    private Long memberId;
}
