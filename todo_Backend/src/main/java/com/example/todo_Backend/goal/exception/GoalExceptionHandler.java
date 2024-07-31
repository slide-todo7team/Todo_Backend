package com.example.todo_Backend.goal.exception;

import com.example.todo_Backend.goal.controller.IndividualGoalController;
import com.example.todo_Backend.goal.dto.GoalResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackageClasses = IndividualGoalController.class)
public class GoalExceptionHandler {

    @ExceptionHandler(TitleLengthException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity<GoalResponse> handler(TitleLengthException e){
        GoalResponse goalResponse = GoalResponse.builder()
                .status(e.getStatus())
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(goalResponse);
    }

    @ExceptionHandler(NotFoundGoalException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private ResponseEntity<GoalResponse> handler(NotFoundGoalException e){
        GoalResponse goalResponse = GoalResponse.builder()
                .status(e.getStatus())
                .message(e.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(goalResponse);
    }
}
