package com.bootcamp.Bolerobootcamp.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class CustomException extends RuntimeException {
    HttpStatusCode statusCode;
    String message;

    public CustomException(HttpStatusCode statusCode, String message)
    {
        super(message);
        this.statusCode = statusCode;
        this.message = message;
    }
}
