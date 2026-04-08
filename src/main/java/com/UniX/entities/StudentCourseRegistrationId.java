package com.UniX.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentCourseRegistrationId implements Serializable {

    private String stdNo;
    private String courseId;
    private Integer semesterId;
}