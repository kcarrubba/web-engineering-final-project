package com.UniX.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.UniX.dtos.EnrolRequest;
import com.UniX.repositories.SemesterRepository;
import com.UniX.repositories.StudentCourseRegistrationRepository;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

import com.UniX.dtos.EnrolRequest;
import com.UniX.dtos.StudentCourseRegistrationDto;
import com.UniX.entities.StudentCourseRegistration;
import com.UniX.repositories.StudentCourseRegistrationRepository;



@RestController
@AllArgsConstructor
@RequestMapping("/enrol")
public class EnrolController {

    private final StudentCourseRegistrationRepository studentCourseRegistrationRepository;

    @PostMapping
    public StudentCourseRegistrationDto enrol(@Valid @RequestBody EnrolRequest enrolRequest) {

        // Create a registration object and save
        StudentCourseRegistration studentCourseRegistration = new StudentCourseRegistration(enrolRequest.getStdNo(), enrolRequest.getSemesterId(), enrolRequest.getCourseId());
        var newRegistration = studentCourseRegistrationRepository.save(studentCourseRegistration);

        // Return a StudentCourseRegistrationDto with the created registration
        var studentCourseRegistrationDto = new StudentCourseRegistrationDto(newRegistration.getStdNo(), newRegistration.getSemesterId(), newRegistration.getCourseId());
        return studentCourseRegistrationDto;
    }

}
