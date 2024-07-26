package com.example.todo_Backend.User.Member.exception;

public class NotFoundRefreshToken extends MemberException{

    public NotFoundRefreshToken(){
        super(MemberStatus.NOT_FOUND_MEMBER_BY_RefreshToken);
    }
}
