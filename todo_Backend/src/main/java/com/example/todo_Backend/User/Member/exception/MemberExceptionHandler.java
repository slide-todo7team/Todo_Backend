package com.example.todo_Backend.User.Member.exception;

import com.example.todo_Backend.User.Member.Controller.MemberController;
import com.example.todo_Backend.User.Member.Dto.MemberResponse;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice(basePackageClasses = MemberController.class)
public class MemberExceptionHandler {

    @ExceptionHandler(DuplicationMemberEmailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity<MemberResponse> handler(DuplicationMemberEmailException e) {

        MemberResponse response = MemberResponse.builder()
                .status(e.getStatus())
                .message(e.getMessage())
                .data(e.getEmail())
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(NotFoundMemberByIdException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity<MemberResponse> handler(NotFoundMemberByIdException e) {

        MemberResponse response = MemberResponse.builder()
                .status(e.getStatus())
                .message(e.getMessage())
                .data(e.getMemberId())
                .build();

        return ResponseEntity.badRequest().body(response);
    }


    @ExceptionHandler(NotEqualPasswordException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity<MemberResponse> handler(NotEqualPasswordException e) {

        MemberResponse response = MemberResponse.builder()
                .status(e.getStatus())
                .message(e.getMessage())
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<MemberResponse> handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Map<String, Object> errors = new HashMap<>();
        for (FieldError error : e.getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }

        MemberResponse response = MemberResponse.builder()
                .status(e.getStatusCode().value())
                .message("fail")
                .data(errors)
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(JwtException.class)
    private ResponseEntity<MemberResponse> jwtRefreshTokenNotValidException(JwtException e) {
        MemberResponse response = MemberResponse.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .message("Refresh Token 만료로 인한 재로그인을 시도해주세요.")
                .build();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private ResponseEntity<MemberResponse> handler(NotFoundException e) {

        MemberResponse response = MemberResponse.builder()
                .status(e.getStatus())
                .message(e.getMessage())
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(NotEmailFormException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private ResponseEntity<MemberResponse> handler(NotEmailFormException e) {

        MemberResponse response = MemberResponse.builder()
                .status(e.getStatus())
                .message(e.getMessage())
                .build();

        return ResponseEntity.badRequest().body(response);
    }

}
