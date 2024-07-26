package com.example.todo_Backend.User.Member.Dto;

import com.example.todo_Backend.User.Member.entity.Member;
import com.example.todo_Backend.User.Member.entity.MemberRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;


@Getter
@ToString
public class MemberSignUpDto {

    @Email
    @NotEmpty(message = "회원 이메일은 필수 입니다.")
    @Schema(description = "이메일 주소입니다.", example = "이메일 주소")
    private String email;

    @NotEmpty(message = "비밀번호는 필수 입니다.")
    @Schema(description = "비밀번호 입니다.", example = "비밀번호 입니다.")
    private String password;

    @Schema(description = "회원의 이름 입니다", example = "닉네임1")
    private String name;


    @Builder
    public Member toEntity() {
        return Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .createDt(LocalDateTime.now())
                .updateDt(LocalDateTime.now())
                .role(MemberRole.USER)
                .build();
    }

}
