package com.ironplug.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class NotMatched extends RuntimeException{
    public NotMatched(String message) {
        super(message);
    }
}
