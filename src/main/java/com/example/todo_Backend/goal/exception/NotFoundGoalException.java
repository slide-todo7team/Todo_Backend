package com.example.todo_Backend.goal.exception;

import lombok.Getter;

@Getter
public class NotFoundGoalException extends GoalException {
    private Long goalId;

    public NotFoundGoalException(Long goalId) {
        super(GoalStatus.NOT_FOUND_GOAL);
        this.goalId = goalId;
    }
}

