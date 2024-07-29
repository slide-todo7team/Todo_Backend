package com.example.todo_Backend.group.exception;

public class GroupException extends RuntimeException {
    private final GroupStatus groupStatus;

    public GroupException(GroupStatus groupStatus) {
        this.groupStatus = groupStatus;
    }

    public int getStatus() {
        return groupStatus.getStatus();
    }

    public String getMessage() {
        return groupStatus.getMessage();
    }
}
