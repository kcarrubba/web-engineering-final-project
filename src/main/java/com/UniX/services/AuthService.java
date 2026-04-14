package com.UniX.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.UniX.dtos.LoginRequest;
import com.UniX.dtos.LoginResponse;
import com.UniX.entities.Student;
import com.UniX.repositories.StudentRepository;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {

    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest request, HttpServletResponse response) {
        Student student = studentRepository.findById(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(request.getPassword(), student.getPasswordHash())) {
            throw new RuntimeException("Invalid username or password");
        }

        String accessToken = jwtService.generateAccessToken(student.getStdNo());
        String refreshToken = jwtService.generateRefreshToken(student.getStdNo());

        //Storing refresh token in an HttpOnly cookie
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setPath("/unix/refresh");
        cookie.setMaxAge(60 * 60); // 7 days
        response.addCookie(cookie);

        return new LoginResponse(accessToken);
    }
}