package com.example.todo_Backend.User.Member.Dto;


import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class MemberFinalSignUpResDto {

    private Long id;
    private String email;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
