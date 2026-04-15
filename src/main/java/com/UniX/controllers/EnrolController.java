package com.UniX.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.UniX.dtos.EnrolRequest;
import com.UniX.repositories.StudentCourseRegistrationRepository;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.UniX.dtos.StudentCourseRegistrationDto;
import com.UniX.entities.StudentCourseRegistration;



@RestController
@AllArgsConstructor
public class EnrolController {

    private final StudentCourseRegistrationRepository studentCourseRegistrationRepository;

    @PostMapping("/unix/enrol")
    public StudentCourseRegistrationDto enrol(@Valid @RequestBody EnrolRequest enrolRequest) {

        // Create a registration object and save
        StudentCourseRegistration studentCourseRegistration = new StudentCourseRegistration(enrolRequest.getStdNo(), enrolRequest.getSemesterId(), enrolRequest.getCourseId());
        var newRegistration = studentCourseRegistrationRepository.save(studentCourseRegistration);

        // Return a StudentCourseRegistrationDto with the created registration
        var studentCourseRegistrationDto = new StudentCourseRegistrationDto(newRegistration.getStdNo(), newRegistration.getSemesterId(), newRegistration.getCourseId());
        return studentCourseRegistrationDto;
    }

}
