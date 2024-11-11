package com.example.stage.api.auth.dto;

import lombok.Data;

@Data
public class ChangePasswordReqDto {
    private String oldPassword;
    private String newPassword;
}
