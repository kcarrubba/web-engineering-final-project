package com.UniX.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CourseOfferings")
@IdClass(CourseOfferingId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseOffering {

    @Id
    @Column(name = "courseID", length = 8)
    private String courseId;

    @Id
    @Column(name = "semesterID")
    private Integer semesterId;

    @Column(name = "maxCapacity")
    private Integer maxCapacity;

    @ManyToOne
    @JoinColumn(name = "courseID", referencedColumnName = "courseID", insertable = false, updatable = false)
    private Course course;

    @ManyToOne
    @JoinColumn(name = "semesterID", referencedColumnName = "semesterID", insertable = false, updatable = false)
    private Semester semester;
}