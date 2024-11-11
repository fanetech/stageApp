package com.example.stage.api.auth;

import com.example.stage.api.auth.dto.LoginResDto;
import com.example.stage.api.auth.dto.RegisterReqDto;
import com.example.stage.api.dtos.Response;
import com.example.stage.api.user.UserModel;
import com.example.stage.api.user.UserRepository;
import com.example.stage.config.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public UserModel registerUser(RegisterReqDto request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        UserModel user = new UserModel();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRoles(Arrays.asList("USER"));

        return userRepository.save(user);
    }

    public Response loginUser(String username, String password) {
            var user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );
            log.warn("Login authentication: {}", authentication);

            SecurityContextHolder.getContext().setAuthentication(authentication);
            LoginResDto loginResDto = new LoginResDto();
            LoginResDto newLoginResDto = loginResDto.getLoginRes(user);
            newLoginResDto.setToken(jwtService.generateToken(user));
            return Response.success(newLoginResDto);

    }

    public void changePassword(String username, String oldPassword, String newPassword) {
        UserModel user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("Invalid old password");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
