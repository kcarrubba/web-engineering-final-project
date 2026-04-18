package com.UniX.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.UniX.dtos.EnrolRequest;
import com.UniX.dtos.StudentCourseRegistrationDto;
import com.UniX.services.EnrolService;

@ExtendWith(MockitoExtension.class)
public class EnrolControllerTest {

    @Mock
    private EnrolService enrolService; 

    @InjectMocks
    private EnrolController enrolController;

    @Test
    public void testEnrolSuccess() {

        EnrolRequest request = new EnrolRequest("c0002", 102, "COMP1140");

        StudentCourseRegistrationDto mockDto = new StudentCourseRegistrationDto("c0002", 102, "COMP1140", "");

        when(enrolService.enrol(any(EnrolRequest.class))).thenReturn(mockDto);
        // Call the controller method
        ResponseEntity<?> response = enrolController.enrol(request); 

        StudentCourseRegistrationDto body = (StudentCourseRegistrationDto) response.getBody();

        // Verify the result
        assertEquals(HttpStatus.CREATED, response.getStatusCode()); 
        assertEquals("c0002", body.getStdNo());
        assertEquals(102, body.getSemesterId());
        assertEquals("COMP1140", body.getCourseId());
        assertEquals("", body.getWarningMessage());
        verify(enrolService, times(1)).enrol(any(EnrolRequest.class));
    }

    @Test
    public void testEnrolFailure() {

        EnrolRequest request = new EnrolRequest("c0002", 102, "COMP1140");

        when(enrolService.enrol(any(EnrolRequest.class)))
        .thenThrow(new RuntimeException("Student is already enrolled in this course for the semester"));

        ResponseEntity<?> response = enrolController.enrol(request);

        // Verify the result
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Student is already enrolled in this course for the semester", response.getBody());
        verify(enrolService, times(1)).enrol(any(EnrolRequest.class));
    }
}