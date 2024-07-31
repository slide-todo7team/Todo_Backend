package com.example.todo_Backend.group.exception;

import com.example.todo_Backend.group.controller.GroupController;
import com.example.todo_Backend.group.dto.GroupResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = GroupController.class)
public class GroupExceptionHandler {

    @ExceptionHandler(DuplicationGroupTitleException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    private ResponseEntity<GroupResponse> handler(DuplicationGroupTitleException e) {
        GroupResponse groupResponse = GroupResponse.builder()
                .status(e.getStatus())
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(groupResponse);
    }

    @ExceptionHandler(NotFoundGroupException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private ResponseEntity<GroupResponse> handler(NotFoundGroupException e) {
        GroupResponse groupResponse = GroupResponse.builder()
                .status(e.getStatus())
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(groupResponse);
    }

    @ExceptionHandler(NotEqualSecretCodeException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private ResponseEntity<GroupResponse> handler(NotEqualSecretCodeException e) {
        GroupResponse groupResponse = GroupResponse.builder()
                .status(e.getStatus())
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(groupResponse);
    }

}
