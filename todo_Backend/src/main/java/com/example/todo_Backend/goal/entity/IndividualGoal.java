package com.example.todo_Backend.goal.entity;

import com.example.todo_Backend.goal.dto.IndividualGoalDto;
import com.example.todo_Backend.goal.exception.TitleLengthException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class IndividualGoal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String title;

    @Column(name="user_id")
    private Long userId;

    private Integer progress = 0;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public IndividualGoal(IndividualGoalDto individualGoalDto) {
        this.title = individualGoalDto.getTitle();
        this.userId = individualGoalDto.getUserId();
    }

}
