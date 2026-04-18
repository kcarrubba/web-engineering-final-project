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
@IdClass(AssumedKnowledgeId.class)
@Table(name = "assumedknowledge")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssumedKnowledge {

    @Id
    @Column(name = "courseID", length = 8)
    private String courseId;

    @Id
    @Column(name = "assumedKnowledge", length = 8)
    private String assumedKnowledgeId;
}