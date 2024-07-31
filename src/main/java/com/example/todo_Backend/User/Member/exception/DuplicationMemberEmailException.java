package com.example.todo_Backend.User.Member.exception;

import lombok.Getter;

@Getter
public class DuplicationMemberEmailException extends MemberException {
    private final String email;

    public DuplicationMemberEmailException(String email) {
        super(MemberStatus.DUPLICATE_MEMBER);
        this.email = email;
    }
}
