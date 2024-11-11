package com.example.stage.api.auth.dto;

import lombok.Data;

@Data
public class RegisterReqDto {
    private String username;
    private String password;
    private String email;
}
