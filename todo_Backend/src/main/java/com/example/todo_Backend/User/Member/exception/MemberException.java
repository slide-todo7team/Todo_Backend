package com.example.todo_Backend.User.Member.exception;

public class MemberException extends RuntimeException {
    private final MemberStatus memberStatus;

    public MemberException(MemberStatus memberStatus) {
        this.memberStatus = memberStatus;
    }

    public int getStatus() {
        return memberStatus.getStatus();
    }

    public String getMessage() {
        return memberStatus.getMessage();
    }
}
