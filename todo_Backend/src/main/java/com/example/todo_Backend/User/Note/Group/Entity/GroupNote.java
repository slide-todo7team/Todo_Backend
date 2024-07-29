package com.example.todo_Backend.User.Note.Group.Entity;

//import com.example.todo_Backend.User.Todo.Entity.GroupModel;
import com.example.todo_Backend.User.Todo.Entity.GroupModel;
import jakarta.persistence.*;
import lombok.Builder;

import java.time.LocalDateTime;

@Entity
@Builder
public class GroupNote {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //todo객체

    @ManyToOne
    @JoinColumn(name="grouptodos_id")
    private GroupModel groupModel;


    private String linkUrl;

    private String content;

    private LocalDateTime updateAt;

    private LocalDateTime createAt;

    private String title;

    private Long memId;

    //그룹 goal 객체
    //

}
