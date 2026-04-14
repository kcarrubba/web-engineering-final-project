package com.UniX.services;

import org.springframework.stereotype.Service;
import com.UniX.dtos.StudentDto;
import com.UniX.repositories.StudentRepository;
import com.UniX.entities.Student;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;

    public StudentDto getStudentByStdNo(String stdNo) {
        Student student = studentRepository.findById(stdNo)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        return new StudentDto(student.getStdNo(), student.getLastname(), student.getGivenNames());
    }
}
