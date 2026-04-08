package com.UniX.repositories;

import com.UniX.entities.CourseOffering;
import com.UniX.entities.CourseOfferingId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseOfferingRepository extends JpaRepository<CourseOffering, CourseOfferingId> {

    List<CourseOffering> findBySemesterId(Integer semesterId);
}