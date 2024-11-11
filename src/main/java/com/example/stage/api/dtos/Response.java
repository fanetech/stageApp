package com.example.stage.api.dtos;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class Response<T> {
    private int status;
    private String message;
    private T data;

    public static <T> Response<T> success(T data) {
        return Response.<T>builder()
                .status(0)
                .message("Success")
                .data(data)
                .build();
    }

    public static <T> Response<T> error(String message) {
        return Response.<T>builder()
                .status(1)
                .message(message)
                .build();
    }
}