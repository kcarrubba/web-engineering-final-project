package com.UniX.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "student")
public class Student {

    @Id
    @Column(name = "stdNo", length = 5)
    private String stdNo;

    @Column(name = "lastname", length = 50)
    private String lastname;

    @Column(name = "givenNames", length = 50)
    private String givenNames;

    @Column(name = "passwordHash", length = 128, nullable = false)
    private String passwordHash;

    @Column(name = "passwordSalt")
    private Double passwordSalt;

    public Student() {
    }

    public Student(String stdNo, String lastname, String givenNames, String passwordHash, Double passwordSalt) {
        this.stdNo = stdNo;
        this.lastname = lastname;
        this.givenNames = givenNames;
        this.passwordHash = passwordHash;
        this.passwordSalt = passwordSalt;
    }

    public String getStdNo() {
        return stdNo;
    }

    public void setStdNo(String stdNo) {
        this.stdNo = stdNo;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getGivenNames() {
        return givenNames;
    }

    public void setGivenNames(String givenNames) {
        this.givenNames = givenNames;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Double getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(Double passwordSalt) {
        this.passwordSalt = passwordSalt;
    }
}