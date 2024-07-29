package com.example.todo_Backend.User.Note.Indiviual.Entity;
//
//import com.example.todo_Backend.User.Todo.Entity.GroupModel;
//import com.example.todo_Backend.User.Todo.Entity.IndividualModel;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class IndiviualNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

//    @ManyToOne
//    @JoinColumn(name="individualtodos_id")
//    private IndividualModel individualModel;

    private String linkUrl;

    private String content;

    private LocalDateTime updateAt;

    private LocalDateTime createAt;

    private String title;

    private int userId;

    //그룹 goal 객체
}
