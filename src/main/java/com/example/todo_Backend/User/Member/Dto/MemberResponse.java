package com.example.todo_Backend.User.Member.Dto;

import com.example.todo_Backend.Common.Response.CommonResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;


import static com.fasterxml.jackson.annotation.JsonInclude.Include;

@Getter
@JsonInclude(Include.NON_NULL)
public class MemberResponse extends CommonResponse {

    private final Object data;

    private final Integer total;

    @Builder
    public MemberResponse(int status, String message, Object data, Integer total) {
        super(status, message);
        this.data = data;
        this.total = total;
    }

    public static MemberResponse ok(Object member) {
        return new MemberResponse(200, "Success", member, null);
    }



    public static MemberResponse error(String message) {
        return new MemberResponse(400, message, null, null);
    }


}
