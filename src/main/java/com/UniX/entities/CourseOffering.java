package com.UniX.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "CourseOfferings")
@IdClass(CourseOfferingId.class)
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

    public CourseOffering() {
    }

    public CourseOffering(String courseId, Integer semesterId, Integer maxCapacity) {
        this.courseId = courseId;
        this.semesterId = semesterId;
        this.maxCapacity = maxCapacity;
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

    public Integer getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(Integer maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public Course getCourse() {
        return course;
    }

    public Semester getSemester() {
        return semester;
    }
}