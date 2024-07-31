package com.example.todo_Backend.goal.entity;

import com.example.todo_Backend.goal.dto.GroupGoalDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class GroupGoal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(name = "group_id")
    private Long groupId;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public GroupGoal(GroupGoalDto groupGoalDto) {
        this.title = groupGoalDto.getTitle();
        this.groupId = groupGoalDto.getGroupId();
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
