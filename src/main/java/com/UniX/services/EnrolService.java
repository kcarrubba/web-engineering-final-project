package com.UniX.services;

import org.springframework.stereotype.Service;
import java.util.List;

import com.UniX.dtos.EnrolRequest;
import com.UniX.dtos.StudentCourseRegistrationDto;
import com.UniX.dtos.StudentDto;
import com.UniX.repositories.CourseRepository;
import com.UniX.repositories.StudentCourseRegistrationRepository;
import com.UniX.repositories.StudentRepository;
import com.UniX.entities.Student;
import com.UniX.entities.StudentCourseRegistration;
import com.UniX.entities.AssumedKnowledge;
import com.UniX.entities.Course;
import com.UniX.entities.CourseOffering;
import com.UniX.entities.PrerequisiteKnowledge;
import com.UniX.repositories.CourseOfferingRepository;
import com.UniX.repositories.PrerequisiteKnowledgeRepository;
import com.UniX.repositories.AssumedKnowledgeRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EnrolService {
    private final StudentCourseRegistrationRepository studentCourseRegistrationRepository;
    private final CourseRepository courseRepository;
    private final CourseOfferingRepository courseOfferingRepository;
    private final PrerequisiteKnowledgeRepository prerequisiteKnowledgeRepository;
    private final AssumedKnowledgeRepository assumedKnowledgeRepository;

    public StudentCourseRegistrationDto enrol(EnrolRequest enrolRequest) {
        if (enrolRequest.getStdNo() == null || enrolRequest.getSemesterId() == null
                || enrolRequest.getCourseId() == null) {
            throw new RuntimeException("All fields in EnrolRequest must be provided");
        }

        if (enrolRequest.getStdNo().isEmpty() || enrolRequest.getSemesterId() <= 0
                || enrolRequest.getCourseId().isEmpty()) {
            throw new RuntimeException("Invalid input values in EnrolRequest");
        }

        // Check if the student is already enrolled in the course for the semester
        boolean alreadyEnrolled = studentCourseRegistrationRepository
                .existsByStdNoAndSemesterIdAndCourseId(enrolRequest.getStdNo(), enrolRequest.getSemesterId(),
                        enrolRequest.getCourseId());
        if (alreadyEnrolled) {
            throw new RuntimeException("Student is already enrolled in this course for the semester");
        }

        List<StudentCourseRegistration> currentEnrolments = studentCourseRegistrationRepository
                .findByStdNoAndSemesterId(enrolRequest.getStdNo(), enrolRequest.getSemesterId());
        List<String> currentEnrolledCourseIds = currentEnrolments.stream().map(StudentCourseRegistration::getCourseId)
                .toList();
        List<Course> currentEnrolledCourses = currentEnrolledCourseIds
                .stream()
                .map(courseId -> {
                    Course course = courseRepository.findByCourseId(courseId);
                    if (course == null)
                        throw new RuntimeException("Course not found for courseId: " + courseId);
                    return course;
                })
                .toList();
        
        int enroledUnits = currentEnrolledCourses.stream().mapToInt(Course::getCredits).sum();
        int totalUnits = enroledUnits + courseRepository.findByCourseId(enrolRequest.getCourseId()).getCredits();

        // Check if the student exceeded the maximum units for the semester when
        // enrolling in this course
        if (totalUnits > 40) {
            throw new RuntimeException(
                    "Enrolling in this course would exceed the maximum allowed units for the semester");
        }

        CourseOffering courseOffering = courseOfferingRepository.findByCourseIdAndSemesterId(enrolRequest.getCourseId(),
                enrolRequest.getSemesterId());
        if (courseOffering == null) {
            throw new RuntimeException("Course offering not found for the given course and semester");
        }

        // Check if course offering exceeds the maximum capacity
        int studentsEnrolledInCourse = studentCourseRegistrationRepository
                .countByCourseIdAndSemesterId(enrolRequest.getCourseId(), enrolRequest.getSemesterId());
        if (studentsEnrolledInCourse >= courseOffering.getMaxCapacity()) {
            throw new RuntimeException("Course offering has reached maximum capacity");
        }

        List<StudentCourseRegistration> completedEnrolments = studentCourseRegistrationRepository
                .findByStdNoAndBeforeSemesterId(enrolRequest.getStdNo(), enrolRequest.getSemesterId());
        List<String> completedCourseIds = completedEnrolments.stream().map(StudentCourseRegistration::getCourseId)
                .toList();

        List<PrerequisiteKnowledge> prerequisiteKnowledges = prerequisiteKnowledgeRepository
                .findByCourseId(enrolRequest.getCourseId());
        List<String> prerequisiteCourseIds = prerequisiteKnowledges.stream()
                .map(PrerequisiteKnowledge::getPrerequisiteId).toList();
        // Check if the student has completed all prerequisite courses for the course they are trying to enrol in
        if (!completedCourseIds.containsAll(prerequisiteCourseIds)) {
            throw new RuntimeException("Student has not completed all prerequisite courses for this course");
        }

        List<AssumedKnowledge> assumedKnowledges = assumedKnowledgeRepository
                .findByCourseId(enrolRequest.getCourseId());
        List<String> assumedCourseIds = assumedKnowledges.stream().map(AssumedKnowledge::getAssumedKnowledgeId)
                .toList();
        // Check if the student has compleated all assumed knowledge for the course they are trying to enrol in
        // If not, we will allow the enrolment but return a warning message in the StudentCourseRegistrationDto
        String warningMessage = "";
        if (!completedCourseIds.containsAll(assumedCourseIds)) {
            warningMessage = "Student has not completed all assumed knowledge for this course. It is recommended to complete the assumed courses before enrolling.";
        }

        // Create a registration object and save
        StudentCourseRegistration studentCourseRegistration = new StudentCourseRegistration(enrolRequest.getStdNo(),
                enrolRequest.getSemesterId(), enrolRequest.getCourseId());
        var newRegistration = studentCourseRegistrationRepository.save(studentCourseRegistration);

        // Return a StudentCourseRegistrationDto with the created registration
        var studentCourseRegistrationDto = new StudentCourseRegistrationDto(newRegistration.getStdNo(),
                newRegistration.getSemesterId(), newRegistration.getCourseId(), warningMessage);
        return studentCourseRegistrationDto;
    }
}
