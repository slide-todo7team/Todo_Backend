package com.example.todo_Backend.User.Todo.Entity;


import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Table(name = "grouptodos")
public class GroupModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private int groupId;

    private Long memId;

    private LocalDateTime updateAt;

    private LocalDateTime createAt;

    private int progress;

    // todo추가 할것
    // private Todo todo
}
