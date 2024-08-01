package com.example.todo_Backend.User.Member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Member {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memId;

    private String email;

    @JsonIgnore
    private String password;

    private String name;

    private MemberRole role;

    private String refreshToken;


    private LocalDateTime createDt;

    private LocalDateTime updateDt;

    public void passwordEncode(PasswordEncoder encoder) {
        this.password = encoder.encode(this.password);
    }

    public void updateRefreshToken(String updateRefreshToken) {
        this.refreshToken = updateRefreshToken;
    }

    public void destroyRefreshToken() {
        this.refreshToken = null;
    }

    public Member socialUpdate(String name) {
        this.name = name;
        return this;
    }
}
