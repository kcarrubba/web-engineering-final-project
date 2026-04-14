package com.UniX.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.UniX.dtos.LoginRequest;
import com.UniX.dtos.LoginResponse;
import com.UniX.dtos.StudentDto;
import com.UniX.entities.Student;
import com.UniX.services.AuthService;
import com.UniX.services.JwtService;
import com.UniX.services.StudentService;

import io.jsonwebtoken.Jwts;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/unix")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;
    private final StudentService studentService;

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