package com.example.likelion13th_spring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class MemberAlreadyExistsException extends RuntimeException{
    public MemberAlreadyExistsException(String message) {
        super(message);
    }
}