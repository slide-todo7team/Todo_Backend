package com.example.todo_Backend.Common.security.signin.response;

import com.example.todo_Backend.Common.Response.CommonResponse;
import lombok.Getter;


@Getter
public class JwtErrorResponse extends CommonResponse {

    private final String code;

    public JwtErrorResponse(int status, String code, String message) {
        super(status, message);
        this.code = code;
    }

    public static JwtErrorResponse error(String code, String message) {
        return new JwtErrorResponse(400, code, message);
    }
}
