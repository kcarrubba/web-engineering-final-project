package com.UniX.repositories;

import com.UniX.entities.StudentCourseRegistration;
import com.UniX.entities.StudentCourseRegistrationId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentCourseRegistrationRepository extends JpaRepository<StudentCourseRegistration, StudentCourseRegistrationId> {
}