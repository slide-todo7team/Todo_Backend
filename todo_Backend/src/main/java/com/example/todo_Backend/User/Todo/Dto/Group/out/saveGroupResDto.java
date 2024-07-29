package com.example.todo_Backend.User.Todo.Dto.Group.out;

import com.example.todo_Backend.User.Todo.Dto.individual.out.saveIndividualResDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter



public class saveGroupResDto {

    private int noteId;

    private boolean done;

    private String title;

    //goaltodo 객체 추가

    private Long userId;

    private LocalDateTime updateAt;

    private LocalDateTime createAt;

    @Builder
    public static saveGroupResDto of(int noteId, boolean done, String title, Long userId, LocalDateTime updateAt, LocalDateTime createAt){

        return saveGroupResDto
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
