package com.UniX.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.UniX.entities.PrerequisiteKnowledge;
import com.UniX.entities.PrerequisiteKnowledgeId;

public interface PrerequisiteKnowledgeRepository extends JpaRepository<PrerequisiteKnowledge, PrerequisiteKnowledgeId>  {
    List<PrerequisiteKnowledge> findByCourseId(String courseId);
}