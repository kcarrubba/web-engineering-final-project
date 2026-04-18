package com.UniX.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@IdClass(PrerequisiteKnowledgeId.class)
@Table(name = "prerequisiteknowledge")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrerequisiteKnowledge {

    @Id
    @Column(name = "courseID", length = 8)
    private String courseId;

    @Id
    @Column(name = "preReqKnowledge", length = 8)
    private String prerequisiteId;
}