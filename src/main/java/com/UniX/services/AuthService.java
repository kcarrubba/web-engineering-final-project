package com.UniX.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.UniX.dtos.LoginRequest;
import com.UniX.dtos.LoginResponse;
import com.UniX.entities.Student;
import com.UniX.repositories.StudentRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest request) {
        Student student = studentRepository.findById(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), student.getPasswordHash())) {
            throw new RuntimeException("Invalid username or password");
        }

        String accessToken = jwtService.generateAccessToken(student.getStdNo());
        String refreshToken = jwtService.generateRefreshToken(student.getStdNo());

        return new LoginResponse(accessToken, accessToken, refreshToken);
    }
}