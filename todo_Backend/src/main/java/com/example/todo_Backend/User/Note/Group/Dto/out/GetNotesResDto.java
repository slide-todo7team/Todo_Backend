package com.example.todo_Backend.User.Note.Group.Dto.out;

import com.example.todo_Backend.User.Note.Group.Entity.GroupNote;

import java.util.List;

public class GetNotesResDto {


    private int nextCuresor;

    private int totalCount;

    private List<GroupNote> noteList;
}
