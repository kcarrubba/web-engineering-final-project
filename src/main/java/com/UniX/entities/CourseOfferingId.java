package com.UniX.entities;

import java.io.Serializable;
import java.util.Objects;

public class CourseOfferingId implements Serializable {

    private String courseId;
    private Integer semesterId;

    public CourseOfferingId() {
    }

    public CourseOfferingId(String courseId, Integer semesterId) {
        this.courseId = courseId;
        this.semesterId = semesterId;
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
        if (!(o instanceof CourseOfferingId)) return false;
        CourseOfferingId that = (CourseOfferingId) o;
        return Objects.equals(courseId, that.courseId) &&
               Objects.equals(semesterId, that.semesterId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseId, semesterId);
    }
}
