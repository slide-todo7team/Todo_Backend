package com.example.todo_Backend.User.Member.Dto;


import lombok.Builder;

@Builder
public class MemberTokenResDto {

    private String refreshToken;

    private String accessToken;

}
