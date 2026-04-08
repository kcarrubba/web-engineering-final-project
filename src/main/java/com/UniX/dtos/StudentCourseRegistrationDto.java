package com.UniX.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StudentCourseRegistrationDto {
    
    private String stdNo;

    private String courseId;

    private Integer semesterId;

    public StudentCourseRegistrationDto(String stdNo, Integer semesterId, String courseId)
    {
        this.stdNo = stdNo;
        this.semesterId = semesterId;
        this.courseId = courseId;
    }
}