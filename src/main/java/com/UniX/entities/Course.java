package com.UniX.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Course")
public class Course {

    @Id
    @Column(name = "courseID", length = 8)
    private String courseId;

    @Column(name = "cName", length = 25, nullable = false, unique = true)
    private String cName;

    @Column(name = "credits")
    private Integer credits;

    public Course() {
    }

    public Course(String courseId, String cName, Integer credits) {
        this.courseId = courseId;
        this.cName = cName;
        this.credits = credits;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }
}