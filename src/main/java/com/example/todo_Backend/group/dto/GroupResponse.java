package com.example.todo_Backend.group.dto;

import com.example.todo_Backend.Common.Response.CommonResponse;
import lombok.Builder;
import lombok.Getter;

@Getter
public class GroupResponse extends CommonResponse {

    @Builder
    public GroupResponse(int status, String message) {
        super(status, message);
    }

    public static GroupResponse ok(){
        return new GroupResponse(200, "OK");
    }

}
