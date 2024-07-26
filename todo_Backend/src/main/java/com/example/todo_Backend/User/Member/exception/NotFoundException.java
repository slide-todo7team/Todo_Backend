package com.example.todo_Backend.User.Member.exception;

import lombok.Getter;
import org.aspectj.weaver.ast.Not;
import org.springframework.data.crossstore.ChangeSetPersister;

@Getter
public class NotFoundException extends  MemberException{


    private String  email;
    public NotFoundException(String email){
        super(MemberStatus.NOT_FOUND_MEMBER_BY_ID);
        this.email = email;
    }
}
