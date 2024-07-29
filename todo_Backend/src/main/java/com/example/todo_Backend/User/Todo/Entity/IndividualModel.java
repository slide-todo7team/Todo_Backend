package com.example.todo_Backend.User.Todo.Entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@Entity
@Table(name = "individualtodos")
public class IndividualModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int noteId;

    private Long userId;


    private boolean done=false;


    @Column(length=30)
    private String title;

    private LocalDateTime updateAt;

    private LocalDateTime createAt;

    //goal 매핑 추가
    //private Goal goal;
}
