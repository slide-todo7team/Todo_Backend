package com.example.todo_Backend.User.Member.exception;

import lombok.Getter;

@Getter
public enum MemberStatus {
    MEMBER_JOIN_FAIL(400, "회원가입에 실패했습니다."),
    DUPLICATE_MEMBER(409, "이미 사용 중인 이메일입니다."),
    NOT_EQUAL_OLD_PASSWORD(400, "기존 비밀번호와 같지 않습니다."),
    NOT_EQUAL_NEW_PASSWORD(400, "새 비밀번호 비교가 다릅니다."),
    NOT_FOUND_MEMBER_BY_ID(404, "유저를 찾을 수 없습니다"),
    NOT_FOUND_MEMBER_BY_EMAIL(400, "회원 이메일과 일치하는 정보가 없습니다."),
    NOT_FOUND_MEMBER_BY_RefreshToken(401,"Unauthorized");

    private final int status;
    private final String message;

    MemberStatus(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
