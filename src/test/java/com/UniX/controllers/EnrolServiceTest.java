package com.UniX.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.UniX.dtos.EnrolRequest;
import com.UniX.dtos.StudentCourseRegistrationDto;
import com.UniX.services.EnrolService;
import com.UniX.repositories.StudentCourseRegistrationRepository;
import com.UniX.repositories.CourseRepository;
import com.UniX.repositories.CourseOfferingRepository;
import com.UniX.repositories.PrerequisiteKnowledgeRepository;
import com.UniX.repositories.AssumedKnowledgeRepository;
import com.UniX.entities.StudentCourseRegistration;
import com.UniX.entities.Course;
import com.UniX.entities.CourseOffering;
import com.UniX.entities.PrerequisiteKnowledge;
import com.UniX.entities.AssumedKnowledge;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertFalse;


@ExtendWith(MockitoExtension.class)
public class EnrolServiceTest {

    @Mock
    private StudentCourseRegistrationRepository studentCourseRegistrationRepository;
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private CourseOfferingRepository courseOfferingRepository;
    @Mock
    private PrerequisiteKnowledgeRepository prerequisiteKnowledgeRepository;
    @Mock
    private AssumedKnowledgeRepository assumedKnowledgeRepository;

    @InjectMocks
    private EnrolService enrolService;

    private EnrolRequest validRequest;

    @BeforeEach
    void setUp() {
        validRequest = new EnrolRequest("c0002", 102, "COMP1140");
    }

    @Test
    public void testEnrolSuccess() {
        when(studentCourseRegistrationRepository
    .existsByStdNoAndSemesterIdAndCourseId(anyString(), anyInt(), anyString()))
    .thenReturn(false);

    when(studentCourseRegistrationRepository
        .findByStdNoAndSemesterId(anyString(), anyInt()))
        .thenReturn(List.of());

    when(courseRepository.findByCourseId(anyString()))
        .thenReturn(new Course("COMP1140", "Computer Science Fundamentals", 6));

    when(courseOfferingRepository
        .findByCourseIdAndSemesterId(anyString(), anyInt()))
        .thenReturn(new CourseOffering("COMP1140", 102, 30, null, null));

    when(studentCourseRegistrationRepository
        .countByCourseIdAndSemesterId(anyString(), anyInt()))
        .thenReturn(10);

    when(studentCourseRegistrationRepository
        .findByStdNoAndBeforeSemesterId(anyString(), anyInt()))
        .thenReturn(List.of());

    when(prerequisiteKnowledgeRepository.findByCourseId(anyString()))
        .thenReturn(List.of());

    when(assumedKnowledgeRepository.findByCourseId(anyString()))
        .thenReturn(List.of());

        StudentCourseRegistration saved = new StudentCourseRegistration("c0002", 102, "COMP1140");
        when(studentCourseRegistrationRepository.save(any())).thenReturn(saved);

        StudentCourseRegistrationDto result = enrolService.enrol(validRequest);

        assertEquals("c0002", result.getStdNo());
        assertEquals(102, result.getSemesterId());
        assertEquals("COMP1140", result.getCourseId());
        assertEquals("", result.getWarningMessage());
    }

    @Test
    public void testEnrolFailureNullFields() {
        EnrolRequest nullRequest = new EnrolRequest(null, null, null);
        
        RuntimeException ex = assertThrows(RuntimeException.class, () -> enrolService.enrol(nullRequest));
        assertEquals("All fields in EnrolRequest must be provided", ex.getMessage());
    }

    @Test
    public void testEnrolFailureAlreadyEnrolled() {
        when(studentCourseRegistrationRepository.existsByStdNoAndSemesterIdAndCourseId(anyString(), anyInt(), anyString())).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> enrolService.enrol(validRequest));
        assertEquals("Student is already enrolled in this course for the semester", ex.getMessage());
    }

    @Test
    public void testEnrolFailureExceedsMaxUnits() {
        when(studentCourseRegistrationRepository.existsByStdNoAndSemesterIdAndCourseId(anyString(), anyInt(), anyString())).thenReturn(false);

        StudentCourseRegistration reg = new StudentCourseRegistration("c0002", 102, "COMP1000");
        when(studentCourseRegistrationRepository.findByStdNoAndSemesterId(anyString(), anyInt())).thenReturn(List.of(reg));
        when(courseRepository.findByCourseId("COMP1000")).thenReturn(new Course("COMP1000","Data Structures",38));
        when(courseRepository.findByCourseId("COMP1140")).thenReturn(new Course("COMP1140","Computer Science Fundamentals",6));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> enrolService.enrol(validRequest));
        assertEquals("Enrolling in this course would exceed the maximum allowed units for the semester", ex.getMessage());
    }

    @Test
    public void testEnrolFailureMaxCapacity() {
        when(studentCourseRegistrationRepository.existsByStdNoAndSemesterIdAndCourseId(anyString(), anyInt(), anyString())).thenReturn(false);
        when(studentCourseRegistrationRepository.findByStdNoAndSemesterId(anyString(), anyInt())).thenReturn(List.of());
        when(courseRepository.findByCourseId(anyString())).thenReturn(new Course("COMP1140", "Computer Science Fundamentals", 6));
        when(courseOfferingRepository.findByCourseIdAndSemesterId(anyString(), anyInt())).thenReturn(new CourseOffering("COMP1140", 102, 30, null, null));
        when(studentCourseRegistrationRepository.countByCourseIdAndSemesterId(anyString(), anyInt())).thenReturn(30); 

        RuntimeException ex = assertThrows(RuntimeException.class, () -> enrolService.enrol(validRequest));
        assertEquals("Course offering has reached maximum capacity", ex.getMessage());
    }

    @Test
    public void testEnrolFailurePrerequisiteNotMet() {
        when(studentCourseRegistrationRepository.existsByStdNoAndSemesterIdAndCourseId(anyString(), anyInt(), anyString())).thenReturn(false);
        when(studentCourseRegistrationRepository.findByStdNoAndSemesterId(anyString(), anyInt())).thenReturn(List.of());
        when(courseRepository.findByCourseId(anyString())).thenReturn(new Course("COMP1140", "Computer Science Fundamentals", 6));
        when(courseOfferingRepository.findByCourseIdAndSemesterId(anyString(), anyInt())).thenReturn(new CourseOffering("COMP1140", 102, 30, null, null));
        when(studentCourseRegistrationRepository.countByCourseIdAndSemesterId(anyString(), anyInt())).thenReturn(10);
        when(studentCourseRegistrationRepository.findByStdNoAndBeforeSemesterId(anyString(), anyInt())).thenReturn(List.of());

        PrerequisiteKnowledge prereq = new PrerequisiteKnowledge("COMP1140", "COMP1000");
        when(prerequisiteKnowledgeRepository.findByCourseId(anyString())).thenReturn(List.of(prereq));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> enrolService.enrol(validRequest));
        assertEquals("Student has not completed all prerequisite courses for this course", ex.getMessage());
    }

    @Test
    public void testEnrolSuccessWithWarning() {
        when(studentCourseRegistrationRepository.existsByStdNoAndSemesterIdAndCourseId(anyString(), anyInt(), anyString())).thenReturn(false);
        when(studentCourseRegistrationRepository.findByStdNoAndSemesterId(anyString(), anyInt())).thenReturn(List.of());
        when(courseRepository.findByCourseId(anyString())).thenReturn(new Course("COMP1140", "Computer Science Fundamentals", 6));
        when(courseOfferingRepository.findByCourseIdAndSemesterId(anyString(), anyInt())).thenReturn(new CourseOffering("COMP1140", 102, 30, null, null));
        when(studentCourseRegistrationRepository.countByCourseIdAndSemesterId(anyString(), anyInt())).thenReturn(10);
        when(studentCourseRegistrationRepository.findByStdNoAndBeforeSemesterId(anyString(), anyInt())).thenReturn(List.of());
        when(prerequisiteKnowledgeRepository.findByCourseId(anyString())).thenReturn(List.of());

        AssumedKnowledge assumed = new AssumedKnowledge("COMP1140", "COMP1000");
        when(assumedKnowledgeRepository.findByCourseId(anyString())).thenReturn(List.of(assumed));

        StudentCourseRegistration saved = new StudentCourseRegistration("c0002", 102, "COMP1140");
        when(studentCourseRegistrationRepository.save(any())).thenReturn(saved);

        StudentCourseRegistrationDto result = enrolService.enrol(validRequest);

        assertEquals(HttpStatus.CREATED, result.getWarningMessage().isEmpty() ? null : HttpStatus.CREATED);
        assertFalse(result.getWarningMessage().isEmpty()); 
    }
}
