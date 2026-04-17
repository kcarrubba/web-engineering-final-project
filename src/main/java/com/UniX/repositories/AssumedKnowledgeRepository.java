package com.UniX.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.UniX.entities.AssumedKnowledge;
import com.UniX.entities.AssumedKnowledgeId;

public interface AssumedKnowledgeRepository extends JpaRepository<AssumedKnowledge, AssumedKnowledgeId>  {
    List<AssumedKnowledge> findByCourseId(String courseId);
}