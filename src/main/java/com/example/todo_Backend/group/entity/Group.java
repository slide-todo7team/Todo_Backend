package com.example.todo_Backend.group.entity;

import com.example.todo_Backend.group.dto.GroupCreateRequestDto;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "`group`")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(name = "create_user")
    private String createUser;

    @Column(name = "secret_code")
    private String secretCode;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    //그룹 생성
    public Group(GroupCreateRequestDto groupCreateRequestDto){
        this.title = groupCreateRequestDto.getTitle();
        this.createUser = groupCreateRequestDto.getCreateUser();
        this.secretCode = groupCreateRequestDto.getSecretCode();
    }

    public Long getGroupId() {
        return id;
    }

    public void setSecretCode(int newCode) {
        this.secretCode = String.valueOf(newCode);
    }
}
