package com.UniX.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.UniX.dtos.LoginRequest;
import com.UniX.dtos.LoginResponse;
import com.UniX.services.AuthService;
import com.UniX.services.JwtService;

import io.jsonwebtoken.Jwts;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/unix")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    @GetMapping("/login")
    public org.springframework.web.servlet.ModelAndView loginPage() {
        return new org.springframework.web.servlet.ModelAndView("forward:/login.html");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            LoginResponse response = authService.login(request);
            return ResponseEntity.ok(response);
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
}