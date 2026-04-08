package com.UniX.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "student")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
}