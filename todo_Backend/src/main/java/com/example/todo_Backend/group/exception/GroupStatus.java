package com.example.todo_Backend.group.exception;

import lombok.Getter;

@Getter
public enum GroupStatus {
    DUPLICATE_GROUP(409, "이미 사용 중인 이름입니다."),
    NOT_EQUAL_SECRET_CODE(404, "없는 초대코드 입니다."),
    NOT_FOUND_GROUP(404,"없는 그룹입니다.");

    private final int status;
    private final String message;

    GroupStatus(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
