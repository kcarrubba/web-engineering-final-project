package com.UniX.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.UniX.dtos.EnrolRequest;
import com.UniX.dtos.StudentCourseRegistrationDto;
import com.UniX.services.EnrolService;

public class EnrolControllerTest {

    static class FakeEnrolService extends EnrolService {
        private final boolean shouldThrow;

        public FakeEnrolService(boolean shouldThrow) {
            super(null, null, null, null, null);
            this.shouldThrow = shouldThrow;
        }

        @Override
        public StudentCourseRegistrationDto enrol(EnrolRequest enrolRequest) {
            if (shouldThrow) {
                throw new RuntimeException("Student is already enrolled in this course for the semester");
            }

            return new StudentCourseRegistrationDto("c0002", 102, "COMP1140", "");
        }
    }

    @Test
    public void testEnrolSuccess() {
        EnrolService enrolService = new FakeEnrolService(false);
        EnrolController enrolController = new EnrolController(enrolService);

        EnrolRequest request = new EnrolRequest("c0002", 102, "COMP1140");

        ResponseEntity<?> response = enrolController.enrol(request);

        StudentCourseRegistrationDto body = (StudentCourseRegistrationDto) response.getBody();

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("c0002", body.getStdNo());
        assertEquals(102, body.getSemesterId());
        assertEquals("COMP1140", body.getCourseId());
        assertEquals("", body.getWarningMessage());
    }

    @Test
    public void testEnrolFailure() {
        EnrolService enrolService = new FakeEnrolService(true);
        EnrolController enrolController = new EnrolController(enrolService);

        EnrolRequest request = new EnrolRequest("c0002", 102, "COMP1140");

        ResponseEntity<?> response = enrolController.enrol(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Student is already enrolled in this course for the semester", response.getBody());
    }
}