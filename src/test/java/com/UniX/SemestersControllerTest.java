package com.UniX;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.UniX.controllers.SemestersController;
import com.UniX.dtos.SemesterDto;
import com.UniX.entities.Semester;
import com.UniX.repositories.SemesterRepository;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class SemestersControllerTest {
    
    @Mock
    private SemesterRepository semesterRepository;

    @InjectMocks
    private SemestersController semestersController;

    @Test
    public void testGetSemestersForEnrolment() {
        // Mock the repository to return a list of semesters
        when(semesterRepository.findByOpenForEnrolmentTrue()).thenReturn(List.of(
            new Semester(1, 1, 2025, true),
            new Semester(2, 2, 2025, true)
        ));

        // Call the controller method
        List<SemesterDto> result = semestersController.getSemestersForEnrolment();

        // Verify the result
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getSemesterId());
        assertEquals(2, result.get(1).getSemesterId());
    }
}
