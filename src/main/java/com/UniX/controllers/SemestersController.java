package com.UniX.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.RestController;

import com.UniX.entities.Semester;
import com.UniX.repositories.SemesterRepository;
import org.springframework.web.bind.annotation.GetMapping;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import com.UniX.dtos.SemesterDto;



@RestController
@AllArgsConstructor
@RequestMapping("/unix/semesters")
public class SemestersController {

    private final SemesterRepository semesterRepository;

    @GetMapping
    public List<SemesterDto> getSemestersForEnrolment() {
        // Return only semesters that are open for enrolment
        return semesterRepository.findByOpenForEnrolmentTrue().stream()
                .map(semester -> new SemesterDto(
                        semester.getSemesterId(),
                        semester.getSemester(),
                        semester.getYear()
                ))
                .toList();
    }
}
