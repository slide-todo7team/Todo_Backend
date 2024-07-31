package com.example.todo_Backend.goal.dto;

import com.example.todo_Backend.Common.Response.CommonResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GoalResponse extends CommonResponse {
    @Builder
    public GoalResponse(int status, String message){super(status,message);}

    public static GoalResponse ok(){ return new GoalResponse(200, "OK");}
}
