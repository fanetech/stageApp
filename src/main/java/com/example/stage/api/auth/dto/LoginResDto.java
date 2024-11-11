package com.example.stage.api.auth.dto;

import com.example.stage.api.user.UserModel;
import lombok.Data;

import java.util.List;

@Data
public class LoginResDto {
    private String username;
    private String email;
    private String token;
    private List<String> roles;
    private List<String> authorities;

    public LoginResDto getLoginRes(UserModel userModel){
        LoginResDto loginResDto = new LoginResDto();
        loginResDto.setUsername(userModel.getUsername());
        loginResDto.setEmail(userModel.getEmail());
        loginResDto.setRoles(userModel.getRoles());
        loginResDto.setAuthorities(
                userModel.getAuthorities().stream()
                        .map(authority -> authority.getAuthority())
                        .toList()
        );
        return loginResDto;
    }
}
