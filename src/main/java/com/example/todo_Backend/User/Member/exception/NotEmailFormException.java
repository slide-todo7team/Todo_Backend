package com.example.todo_Backend.User.Member.exception;



public class NotEmailFormException extends MemberException{

    public NotEmailFormException() {
        super(MemberStatus.NOT_EMAIL_FORM);
    }
}
