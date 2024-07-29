package com.example.todo_Backend.group.exception;

import lombok.Getter;

@Getter
public class DuplicationGroupTitleException extends GroupException {
    private final String title;


    public DuplicationGroupTitleException(String title) {
        super(GroupStatus.DUPLICATE_GROUP);
        this.title = title;
    }
}
