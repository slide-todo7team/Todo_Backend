package com.example.todo_Backend.Common.Response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ErrorResponse extends CommonResponse {

    @Builder
    public ErrorResponse(int status, String message) {
        super(status, message);
    }

}
