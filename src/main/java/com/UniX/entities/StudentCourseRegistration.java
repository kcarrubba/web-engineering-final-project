package com.UniX.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "StudentCourseRegistration")
@IdClass(StudentCourseRegistrationId.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentCourseRegistration {

    @Id
    @Column(name = "stdNo", length = 5)
    private String stdNo;

    @Id
    @Column(name = "courseID", length = 8)
    private String courseId;

    @Id
    @Column(name = "semesterID")
    private Integer semesterId;

    @Column(name = "grade", length = 2)
    private String grade;

    @Column(name = "mark", precision = 5, scale = 2)
    private BigDecimal mark;

    @ManyToOne
    @JoinColumn(name = "stdNo", referencedColumnName = "stdNo", insertable = false, updatable = false)
    private Student student;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "courseID", referencedColumnName = "courseID", insertable = false, updatable = false),
        @JoinColumn(name = "semesterID", referencedColumnName = "semesterID", insertable = false, updatable = false)
    })
    private CourseOffering courseOffering;
}