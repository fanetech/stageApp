package com.example.stage.exception;

import com.example.stage.api.dtos.Response;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Response<Void> handleException(Exception e) {
        return Response.error(e.getMessage());
    }
}