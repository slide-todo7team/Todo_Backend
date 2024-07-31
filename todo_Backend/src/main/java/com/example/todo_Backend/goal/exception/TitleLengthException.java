package com.example.todo_Backend.goal.exception;

import lombok.Getter;

@Getter
public class TitleLengthException extends GoalException{
    private String title;

    public TitleLengthException(String title) {
        super(GoalStatus.WRONG_TITLE_LENGTH);
        this.title = title;
    }
}
