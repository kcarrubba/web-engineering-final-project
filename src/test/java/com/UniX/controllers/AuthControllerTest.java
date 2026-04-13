package com.UniX.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.UniX.dtos.LoginRequest;
import com.UniX.dtos.LoginResponse;
import com.UniX.services.AuthService;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private AuthService authService;

    @InjectMocks
    private AuthController authController;

    @Test
    public void testLoginSuccess() {
        LoginRequest request = new LoginRequest();
        request.setUsername("c0002");
        request.setPassword("myPassword");

        LoginResponse response = new LoginResponse(
                "access-token-value",
                "access-token-value",
                "refresh-token-value"
        );

        when(authService.login(request)).thenReturn(response);

        ResponseEntity<?> result = authController.login(request);

        assertEquals(HttpStatus.OK, result.getStatusCode());

        LoginResponse body = (LoginResponse) result.getBody();
        assertEquals("access-token-value", body.getToken());
        assertEquals("access-token-value", body.getAccessToken());
        assertEquals("refresh-token-value", body.getRefreshToken());
    }

    @Test
    public void testLoginFailure() {
        LoginRequest request = new LoginRequest();
        request.setUsername("c0002");
        request.setPassword("wrongPassword");

        when(authService.login(request)).thenThrow(new RuntimeException("Invalid username or password"));

        ResponseEntity<?> result = authController.login(request);

        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
        assertEquals("Invalid username or password", result.getBody());
    }
}