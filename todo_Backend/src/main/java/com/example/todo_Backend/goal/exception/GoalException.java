package com.example.todo_Backend.goal.exception;

import com.example.todo_Backend.group.exception.GroupException;
import com.example.todo_Backend.group.exception.GroupStatus;

public class GoalException extends RuntimeException {
    private final GoalStatus goalStatus;

    public GoalException(GoalStatus goalStatus) {
        this.goalStatus = goalStatus;
    }

    public int getStatus(){return goalStatus.getStatus();}
    public String getMessage(){return goalStatus.getMessage();}


}
