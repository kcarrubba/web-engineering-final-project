package com.UniX.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssumedKnowledgeId implements Serializable {

    private String courseId;
    private String assumedKnowledgeId;
}