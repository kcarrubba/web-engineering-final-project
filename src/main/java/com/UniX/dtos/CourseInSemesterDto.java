package com.UniX.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseInSemesterDto {
    private String courseId;
    private String courseName;
    private Integer credits;
    private Integer maxCapacity;
}