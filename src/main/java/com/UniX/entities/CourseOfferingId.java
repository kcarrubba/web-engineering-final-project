package com.UniX.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseOfferingId implements Serializable {

    private String courseId;
    private Integer semesterId;
}