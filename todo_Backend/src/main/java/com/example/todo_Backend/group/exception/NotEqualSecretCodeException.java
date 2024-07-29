package com.example.todo_Backend.group.exception;

import lombok.Getter;

@Getter
public class NotEqualSecretCodeException extends GroupException {
    private final String secretCode;

    public NotEqualSecretCodeException(String secretCode) {
        super(GroupStatus.NOT_EQUAL_SECRET_CODE);
        this.secretCode = secretCode;
    }
}
