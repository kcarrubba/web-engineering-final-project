package com.UniX.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Course")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Course {

    @Id
    @Column(name = "courseID", length = 8)
    private String courseId;

    @Column(name = "cName", length = 25, nullable = false, unique = true)
    private String cName;

    @Column(name = "credits")
    private Integer credits;
}