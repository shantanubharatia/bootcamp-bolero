package com.bootcamp.Bolerobootcamp.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> handleApplicationException(CustomException ex) {

        return ResponseEntity.status(ex.getStatusCode()).body(ex.getMessage());
    }
}