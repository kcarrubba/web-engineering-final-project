package com.UniX.dtos;

import java.util.List;
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
    private Integer seatsAvailable;
    private List<String> prerequisites;
    private List<String> assumedKnowledge;
    private Boolean alreadyEnrolled;
}