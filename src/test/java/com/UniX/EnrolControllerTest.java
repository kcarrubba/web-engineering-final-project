package com.UniX;

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

import com.UniX.controllers.EnrolController;
import com.UniX.dtos.EnrolRequest;
import com.UniX.entities.StudentCourseRegistration;
import com.UniX.repositories.StudentCourseRegistrationRepository;
import com.UniX.dtos.StudentCourseRegistrationDto;

@ExtendWith(MockitoExtension.class)
public class EnrolControllerTest {

        @Mock
        private StudentCourseRegistrationRepository repository;

        @InjectMocks
        private EnrolController enrolController;
    
        @Test
        public void testEnrol() {

            EnrolRequest request = new EnrolRequest("c0002", 102, "COMP1140");

            StudentCourseRegistration saved = new StudentCourseRegistration("c0002", 102, "COMP1140");

            // Mock the repository to return the saved registration
            when(repository.save(any(StudentCourseRegistration.class))).thenReturn(saved);
            
            // Call the controller method
            StudentCourseRegistrationDto result = enrolController.enrol(request);
    
            // Verify the result
            assertEquals("c0002", result.getStdNo());
            assertEquals(102, result.getSemesterId());
            assertEquals("COMP1140", result.getCourseId());
            verify(repository, times(1))
                    .save(any(StudentCourseRegistration.class));
        }
}
