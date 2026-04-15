package com.UniX.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.UniX.dtos.LoginRequest;
import com.UniX.dtos.LoginResponse;
import com.UniX.dtos.StudentDto;
import com.UniX.services.AuthService;
import com.UniX.services.JwtService;
import com.UniX.services.StudentService;

import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;
    private final StudentService studentService;

    @GetMapping("/login")
    public org.springframework.web.servlet.ModelAndView loginPage() {
        return new org.springframework.web.servlet.ModelAndView("forward:/login.html");
    }

    @PostMapping("/unix/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request, HttpServletResponse response) {
        try {
            LoginResponse loginResponse = authService.login(request, response);
            return ResponseEntity.ok(loginResponse);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username or password");
        }
    }

    @PostMapping("/validate")
    public boolean validateToken(@RequestHeader("Authorization") String authToken) {
        var token = authToken.replace("Bearer ", "");
        return jwtService.validateAccessToken(token) != null;
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(HttpServletRequest request) {
        
        // Find the refreshToken cookie
        String refreshToken = null;
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("refreshToken")) {
                refreshToken = cookie.getValue();
                break;
            }
        }

        if (refreshToken == null) return ResponseEntity.status(401).body("Missing refresh token");

        // Check whether refresh token in valid
        String username = jwtService.validateRefreshToken(refreshToken);
        if (username == null) return ResponseEntity.status(401).body("Invalid refresh token");

        String newAccessToken = jwtService.generateAccessToken(username);
        return ResponseEntity.ok(new LoginResponse(newAccessToken));
    } 

    // New endpoint to get current user info i.e. Student Name & Username (StuNo)
    @GetMapping("/me")
    public ResponseEntity<StudentDto> me(){
     
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();

        StudentDto student = studentService.getStudentByStdNo(username);
        if (student == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(student);
    }
}