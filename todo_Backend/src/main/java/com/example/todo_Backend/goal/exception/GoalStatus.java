package com.example.todo_Backend.goal.exception;

import lombok.Getter;

@Getter
public enum GoalStatus {
    WRONG_TITLE_LENGTH(400,"title은 쵀대 30자 이하로 작성해 주세요."),
    NOT_FOUND_GOAL(404, "목표를 찾을 수 없습니다.");

    private final int status;
    private final String message;

    GoalStatus(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
