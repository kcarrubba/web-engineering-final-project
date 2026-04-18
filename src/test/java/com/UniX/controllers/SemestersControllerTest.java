package com.UniX.controllers;

import com.UniX.dtos.CourseInSemesterDto;
import com.UniX.entities.Semester;
import com.UniX.repositories.SemesterRepository;
import com.UniX.services.CourseOfferingService;
import com.UniX.services.SemesterService;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class SemestersControllerTest {

    static class FakeCourseOfferingService extends CourseOfferingService {
        private final List<CourseInSemesterDto> coursesToReturn;

        public FakeCourseOfferingService(List<CourseInSemesterDto> coursesToReturn) {
            super(null, null, null, null);
            this.coursesToReturn = coursesToReturn;
        }

        @Override
        public List<CourseInSemesterDto> getCoursesBySemesterId(Integer semesterId, String stdNo) {
            return coursesToReturn;
        }
    }

    private MockMvc mockMvc;
    private SemesterRepository semesterRepository;

    @BeforeEach
    void setUp() {
        semesterRepository = mock(SemesterRepository.class);
    }

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("GET /unix/semesters returns only semesters open for enrolment")
    void getSemestersForEnrolment_returnsOpenSemestersOnly() throws Exception {
        List<Semester> semesters = List.of(
                new Semester(101, 1, 2026, true),
                new Semester(102, 2, 2026, true)
        );

        when(semesterRepository.findByOpenForEnrolmentTrue()).thenReturn(semesters);

        SemesterService semesterService = new SemesterService(semesterRepository);
        CourseOfferingService fakeCourseOfferingService = new FakeCourseOfferingService(List.of());
        SemestersController semestersController =
                new SemestersController(semesterService, fakeCourseOfferingService);

        mockMvc = MockMvcBuilders.standaloneSetup(semestersController).build();

        mockMvc.perform(get("/unix/semesters"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].semesterId").value(101))
                .andExpect(jsonPath("$[0].semester").value(1))
                .andExpect(jsonPath("$[0].year").value(2026))
                .andExpect(jsonPath("$[1].semesterId").value(102))
                .andExpect(jsonPath("$[1].semester").value(2))
                .andExpect(jsonPath("$[1].year").value(2026));

        verify(semesterRepository).findByOpenForEnrolmentTrue();
    }

    @Test
    @DisplayName("GET /unix/semesters/102/courses returns courses for the semester")
    void getCoursesInSemester_returnsCourses() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("s1234567", null, List.of())
        );

        List<CourseInSemesterDto> courses = List.of(
                new CourseInSemesterDto("COMP1140", "Database Management", 10, 3, List.of(), List.of(), false),
                new CourseInSemesterDto("SENG1110", "Programming", 10, 0, List.of(), List.of(), false),
                new CourseInSemesterDto("SENG2050", "Web Engineering", 10, 5, List.of(), List.of(), true)
        );

        SemesterService semesterService = new SemesterService(semesterRepository);
        CourseOfferingService fakeCourseOfferingService = new FakeCourseOfferingService(courses);
        SemestersController semestersController =
                new SemestersController(semesterService, fakeCourseOfferingService);

        mockMvc = MockMvcBuilders.standaloneSetup(semestersController).build();

        mockMvc.perform(get("/unix/semesters/102/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].courseId").value("COMP1140"))
                .andExpect(jsonPath("$[0].courseName").value("Database Management"))
                .andExpect(jsonPath("$[0].credits").value(10))
                .andExpect(jsonPath("$[0].seatsAvailable").value(3))
                .andExpect(jsonPath("$[1].courseId").value("SENG1110"))
                .andExpect(jsonPath("$[1].seatsAvailable").value(0))
                .andExpect(jsonPath("$[2].courseId").value("SENG2050"))
                .andExpect(jsonPath("$[2].alreadyEnrolled").value(true));
    }

    @Test
    @DisplayName("GET /unix/semesters/999/courses returns empty list")
    void getCoursesInSemester_returnsEmptyList() throws Exception {
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken("s1234567", null, List.of())
        );

        SemesterService semesterService = new SemesterService(semesterRepository);
        CourseOfferingService fakeCourseOfferingService = new FakeCourseOfferingService(List.of());
        SemestersController semestersController =
                new SemestersController(semesterService, fakeCourseOfferingService);

        mockMvc = MockMvcBuilders.standaloneSetup(semestersController).build();

        mockMvc.perform(get("/unix/semesters/999/courses"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}