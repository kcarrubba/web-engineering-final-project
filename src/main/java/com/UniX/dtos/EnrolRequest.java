package com.UniX.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EnrolRequest {
    
    @NotNull(message = "Student number is required")
    private String stdNo;

    @NotNull(message = "Semester ID is required")
    private Integer semesterId;

    @NotNull(message = "Course ID is required")
    private String courseId;


}
