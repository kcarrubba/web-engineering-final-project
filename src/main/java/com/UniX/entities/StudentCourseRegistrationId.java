package com.UniX.entities;

import java.io.Serializable;
import java.util.Objects;

public class StudentCourseRegistrationId implements Serializable {

    private String stdNo;
    private String courseId;
    private Integer semesterId;

    public StudentCourseRegistrationId() {
    }

    public StudentCourseRegistrationId(String stdNo, String courseId, Integer semesterId) {
        this.stdNo = stdNo;
        this.courseId = courseId;
        this.semesterId = semesterId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StudentCourseRegistrationId)) return false;
        StudentCourseRegistrationId that = (StudentCourseRegistrationId) o;
        return Objects.equals(stdNo, that.stdNo) &&
               Objects.equals(courseId, that.courseId) &&
               Objects.equals(semesterId, that.semesterId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stdNo, courseId, semesterId);
    }
}