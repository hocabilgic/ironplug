package com.ironplug.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException  extends RuntimeException{

    public ResourceNotFoundException(String message) {
        super(message);
    }
    public ResourceNotFoundException(String resource, String fieldName, Long fieldValue) {
        super(String.format("%s not found with %s : '%s'", resource, fieldName, fieldValue));
    }
    public ResourceNotFoundException(String resource, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'", resource, fieldName, fieldValue.toString()));
    }


}
