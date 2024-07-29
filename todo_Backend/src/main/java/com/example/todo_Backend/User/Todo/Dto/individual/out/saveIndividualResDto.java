package com.example.todo_Backend.User.Todo.Dto.individual.out;


import lombok.Builder;

import java.time.LocalDateTime;


public class saveIndividualResDto {


    private int noteId;

    private boolean done;

    private String title;

    //goaltodo 객체 추가

    private Long userId;

    private LocalDateTime updateAt;

    private LocalDateTime createAt;

    @Builder
    public static saveIndividualResDto of(int noteId,boolean done,String title,Long userId,LocalDateTime updateAt,LocalDateTime createAt){

        return saveIndividualResDto
                .builder()
                .noteId(noteId)
                .done(done)
                .title(title)
                .userId(userId)
                .updateAt(updateAt)
                .createAt(createAt)
                .build();

    }


}
