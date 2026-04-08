package com.UniX.controllers;

import com.UniX.entities.Semester;
import com.UniX.repositories.SemesterRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    private final SemesterRepository semesterRepository;

    public TestController(SemesterRepository semesterRepository) {
        this.semesterRepository = semesterRepository;
    }

    @GetMapping("/test")
    public List<Semester> testDatabaseConnection() {
        return semesterRepository.findAll();
    }
}