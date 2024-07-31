package com.example.todo_Backend.group.exception;

import lombok.Getter;

@Getter
public class NotFoundGroupException extends GroupException {

    private final Long groupId;

    public NotFoundGroupException(Long groupId) {
        super(GroupStatus.NOT_FOUND_GROUP);
        this.groupId = groupId;
    }

}