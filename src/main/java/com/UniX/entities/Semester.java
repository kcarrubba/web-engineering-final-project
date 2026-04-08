package com.UniX.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Semester")
public class Semester {

    @Id
    @Column(name = "semesterID")
    private Integer semesterId;

    @Column(name = "semester")
    private Integer semester;

    @Column(name = "year")
    private Integer year;

    @Column(name = "openForEnrolment")
    private Boolean openForEnrolment;

    public Semester() {
    }

    public Semester(Integer semesterId, Integer semester, Integer year, Boolean openForEnrolment) {
        this.semesterId = semesterId;
        this.semester = semester;
        this.year = year;
        this.openForEnrolment = openForEnrolment;
    }

    public Integer getSemesterId() {
        return semesterId;
    }

    public void setSemesterId(Integer semesterId) {
        this.semesterId = semesterId;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Boolean getOpenForEnrolment() {
        return openForEnrolment;
    }

    public void setOpenForEnrolment(Boolean openForEnrolment) {
        this.openForEnrolment = openForEnrolment;
    }
}
