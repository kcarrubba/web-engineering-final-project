package com.UniX.repositories;

import com.UniX.entities.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SemesterRepository extends JpaRepository<Semester, Integer> {
    List<Semester> findByOpenForEnrolmentTrue();
}