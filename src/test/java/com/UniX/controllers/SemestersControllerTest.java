package com.UniX.controllers;

import com.UniX.dtos.CourseInSemesterDto;
import com.UniX.entities.Semester;
import com.UniX.repositories.SemesterRepository;
import com.UniX.services.CourseOfferingService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SemestersController.class)
public class SemestersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SemesterRepository semesterRepository;

    @MockBean
    private CourseOfferingService courseOfferingService;

    @Test
    @DisplayName("GET /unix/semesters/102/courses returns courses for the semester")
    void getCoursesInSemester_returnsCourses() throws Exception {
        List<CourseInSemesterDto> courses = List.of(
                new CourseInSemesterDto("COMP1140", "Database Management", 10, 10),
                new CourseInSemesterDto("SENG1110", "Programming", 10, 10),
                new CourseInSemesterDto("SENG2050", "Web Engineering", 10, 10)
        );

        when(courseOfferingService.getCoursesBySemesterId(102)).thenReturn(courses);

        mockMvc.perform(get("/unix/semesters/102/courses"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].courseId").value("COMP1140"))
                .andExpect(jsonPath("$[0].courseName").value("Database Management"))
                .andExpect(jsonPath("$[0].credits").value(10))
                .andExpect(jsonPath("$[0].maxCapacity").value(10))
                .andExpect(jsonPath("$[1].courseId").value("SENG1110"))
                .andExpect(jsonPath("$[2].courseId").value("SENG2050"));

        verify(courseOfferingService).getCoursesBySemesterId(eq(102));
    }

    @Test
    @DisplayName("GET /unix/semesters/999/courses returns empty list when no courses exist")
    void getCoursesInSemester_returnsEmptyList() throws Exception {
        when(courseOfferingService.getCoursesBySemesterId(999)).thenReturn(List.of());

        mockMvc.perform(get("/unix/semesters/999/courses"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(content().json("[]"));

        verify(courseOfferingService).getCoursesBySemesterId(eq(999));
    }

    @Test
    @DisplayName("GET /unix/semesters returns all semesters")
    void getSemesters_returnsAllSemesters() throws Exception {
        List<Semester> semesters = List.of(
                new Semester(100, 2, 2025, false),
                new Semester(101, 1, 2026, true),
                new Semester(102, 2, 2026, true)
        );

        when(semesterRepository.findAll()).thenReturn(semesters);

        mockMvc.perform(get("/unix/semesters"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].semesterId").value(100))
                .andExpect(jsonPath("$[1].openForEnrolment").value(true));
    }
}