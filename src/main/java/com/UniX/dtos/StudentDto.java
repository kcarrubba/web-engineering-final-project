package com.UniX.dtos;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StudentDto {
    
    private String stdNo;
    private String lastname;
    private String givenNames;
}
