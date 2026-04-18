package com.UniX.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class StudentCourseRegistrationDto {
    
    private String stdNo;

    private String courseId;

    private Integer semesterId;

    private String warningMessage;

    public StudentCourseRegistrationDto(String stdNo, Integer semesterId, String courseId, String warningMessage)
    {
        this.stdNo = stdNo;
        this.semesterId = semesterId;
        this.courseId = courseId;
        this.warningMessage = warningMessage;
    }
}