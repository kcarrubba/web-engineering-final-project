package com.UniX.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.UniX.dtos.CourseInSemesterDto;
import com.UniX.entities.AssumedKnowledge;
import com.UniX.entities.CourseOffering;
import com.UniX.entities.PrerequisiteKnowledge;
import com.UniX.repositories.AssumedKnowledgeRepository;
import com.UniX.repositories.CourseOfferingRepository;
import com.UniX.repositories.PrerequisiteKnowledgeRepository;
import com.UniX.repositories.StudentCourseRegistrationRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CourseOfferingService {

    private final CourseOfferingRepository courseOfferingRepository;
    private final StudentCourseRegistrationRepository studentCourseRegistrationRepository;
    private final PrerequisiteKnowledgeRepository prerequisiteKnowledgeRepository;
    private final AssumedKnowledgeRepository assumedKnowledgeRepository;

    public List<CourseInSemesterDto> getCoursesBySemesterId(Integer semesterId, String stdNo) {
        List<CourseOffering> offerings = courseOfferingRepository.findBySemesterId(semesterId);

        return offerings.stream()
                .map(offering -> {
                    int enrolledCount = studentCourseRegistrationRepository
                            .countByCourseIdAndSemesterId(offering.getCourseId(), semesterId);

                    int seatsAvailable = offering.getMaxCapacity() - enrolledCount;

                    List<String> prerequisites = prerequisiteKnowledgeRepository
                            .findByCourseId(offering.getCourseId())
                            .stream()
                            .map(PrerequisiteKnowledge::getPrerequisiteId)
                            .toList();

                    List<String> assumedKnowledge = assumedKnowledgeRepository
                            .findByCourseId(offering.getCourseId())
                            .stream()
                            .map(AssumedKnowledge::getAssumedKnowledgeId)
                            .toList();

                    boolean alreadyEnrolled = studentCourseRegistrationRepository
                            .existsByStdNoAndSemesterIdAndCourseId(stdNo, semesterId, offering.getCourseId());

                    return new CourseInSemesterDto(
                            offering.getCourseId(),
                            offering.getCourse().getCName(),
                            offering.getCourse().getCredits(),
                            seatsAvailable,
                            prerequisites,
                            assumedKnowledge,
                            alreadyEnrolled
                    );
                })
                .toList();
    }
}