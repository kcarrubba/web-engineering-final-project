package com.UniX.repositories;

import com.UniX.entities.StudentCourseRegistration;
import com.UniX.entities.StudentCourseRegistrationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentCourseRegistrationRepository extends JpaRepository<StudentCourseRegistration, StudentCourseRegistrationId> {
  List<StudentCourseRegistration> findByStdNoAndSemesterId(String stdNo, int semesterId);
    int countByCourseIdAndSemesterId(String courseId, int semesterId);
    boolean existsByStdNoAndSemesterIdAndCourseId(String stdNo, int semesterId, String courseId);
    @Query("SELECT scr FROM StudentCourseRegistration scr " +
       "WHERE scr.stdNo = :stdNo " +
       "AND scr.semesterId < :semesterId")
    List<StudentCourseRegistration> findByStdNoAndBeforeSemesterId(
        @Param("stdNo") String stdNo, @Param("semesterId") int semesterId);
}