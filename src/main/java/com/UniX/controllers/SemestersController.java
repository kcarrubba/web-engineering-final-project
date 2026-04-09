package com.UniX.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.UniX.dtos.CourseInSemesterDto;
import com.UniX.dtos.SemesterDto;
import com.UniX.repositories.SemesterRepository;
import com.UniX.services.CourseOfferingService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/unix/semesters")
public class SemestersController {

    private final SemesterRepository semesterRepository;
    private final CourseOfferingService courseOfferingService;

    @GetMapping
    public List<SemesterDto> getSemestersForEnrolment() {
        return semesterRepository.findByOpenForEnrolmentTrue().stream()
                .map(semester -> new SemesterDto(
                        semester.getSemesterId(),
                        semester.getSemester(),
                        semester.getYear()
                ))
                .toList();
    }

    @GetMapping("/{semesterId}/courses")
    public ResponseEntity<List<CourseInSemesterDto>> getCoursesInSemester(@PathVariable Integer semesterId) {
        List<CourseInSemesterDto> courses = courseOfferingService.getCoursesBySemesterId(semesterId);
        return ResponseEntity.ok(courses);
    }
}