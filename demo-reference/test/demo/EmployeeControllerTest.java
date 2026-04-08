package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.example.demo.controllers.EmployeeController;
import com.example.demo.dtos.EmployeeDto;
import com.example.demo.entities.Employee;
import com.example.demo.repositories.EmployeeRepository;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeController employeeController;

    @Test
    void testGetEmployeeById()
    {
        when(employeeRepository.findById(1)).thenReturn(Optional.of(new Employee("Alice", 70000)));
        ResponseEntity<EmployeeDto> employee = employeeController.getEmployee(1);
        assertEquals("Alice", employee.getBody().getName());
        
    }

}
