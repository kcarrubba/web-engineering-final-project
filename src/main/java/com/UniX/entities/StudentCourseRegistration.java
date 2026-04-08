package com.UniX.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "StudentCourseRegistration")
@IdClass(StudentCourseRegistrationId.class)
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

    public StudentCourseRegistration() {
    }

    public StudentCourseRegistration(String stdNo, String courseId, Integer semesterId, String grade, BigDecimal mark) {
        this.stdNo = stdNo;
        this.courseId = courseId;
        this.semesterId = semesterId;
        this.grade = grade;
        this.mark = mark;
    }

    public String getStdNo() {
        return stdNo;
    }

    public void setStdNo(String stdNo) {
        this.stdNo = stdNo;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public Integer getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(Integer semesterId) {
        this.semesterId = semesterId;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public BigDecimal getMark() {
        return mark;
    }

    public void setMark(BigDecimal mark) {
        this.mark = mark;
    }

    public Student getStudent() {
        return student;
    }

    public CourseOffering getCourseOffering() {
        return courseOffering;
    }
}