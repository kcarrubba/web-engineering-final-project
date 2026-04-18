package com.UniX.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.UniX.dtos.LoginRequest;
import com.UniX.dtos.LoginResponse;
import com.UniX.services.AuthService;
import com.UniX.services.JwtService;
import com.UniX.services.StudentService;

import jakarta.servlet.http.HttpServletResponse;

public class AuthControllerTest {

    static class FakeAuthService extends AuthService {
        private final boolean shouldThrow;

        public FakeAuthService(boolean shouldThrow) {
            super(null, null, null);
            this.shouldThrow = shouldThrow;
        }

        @Override
        public LoginResponse login(LoginRequest request, HttpServletResponse response) {
            if (shouldThrow) {
                throw new RuntimeException("Invalid username or password");
            }
            return new LoginResponse("access-token-value");
        }
    }

    @Test
    public void testLoginSuccess() {
        AuthService authService = new FakeAuthService(false);
        AuthController authController = new AuthController(authService, null, null);

        LoginRequest request = new LoginRequest();
        request.setUsername("c0002");
        request.setPassword("myPassword");

        HttpServletResponse httpResponse = mock(HttpServletResponse.class);

        ResponseEntity<?> result = authController.login(request, httpResponse);

        assertEquals(HttpStatus.OK, result.getStatusCode());

        LoginResponse body = (LoginResponse) result.getBody();
        assertEquals("access-token-value", body.getAccessToken());
    }

    @Test
    public void testLoginFailure() {
        AuthService authService = new FakeAuthService(true);
        AuthController authController = new AuthController(authService, null, null);

        LoginRequest request = new LoginRequest();
        request.setUsername("c0002");
        request.setPassword("wrongPassword");

        HttpServletResponse httpResponse = mock(HttpServletResponse.class);

        ResponseEntity<?> result = authController.login(request, httpResponse);

        assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
        assertEquals("Invalid username or password", result.getBody());
    }
}