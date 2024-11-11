package com.example.stage.api.auth;

import com.example.stage.api.auth.dto.AuthReqDto;
import com.example.stage.api.auth.dto.ChangePasswordReqDto;
import com.example.stage.api.auth.dto.LoginResDto;
import com.example.stage.api.auth.dto.RegisterReqDto;
import com.example.stage.api.dtos.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterReqDto request) {
        authService.registerUser(request);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody AuthReqDto request) {
        return ResponseEntity.ok(authService.loginUser(request.getUsername(), request.getPassword()));
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordReqDto request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authService.changePassword(
                authentication.getName(),
                request.getOldPassword(),
                request.getNewPassword()
        );
        return ResponseEntity.ok("Password changed successfully");
    }
}
