package com.UniX.controllers;

import org.springframework.web.bind.annotation.RestController;
import com.UniX.dtos.EnrolRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.UniX.dtos.StudentCourseRegistrationDto;
import com.UniX.services.EnrolService;;

@RestController
@AllArgsConstructor
public class EnrolController {
    private final EnrolService enrolService;

    @PostMapping("/unix/enrol")
    public ResponseEntity<?> enrol(@Valid @RequestBody EnrolRequest enrolRequest) {
        try {
            StudentCourseRegistrationDto result = enrolService.enrol(enrolRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}