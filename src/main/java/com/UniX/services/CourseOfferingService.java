package com.UniX.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.UniX.dtos.CourseInSemesterDto;
import com.UniX.entities.CourseOffering;
import com.UniX.repositories.CourseOfferingRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CourseOfferingService {

    private final CourseOfferingRepository courseOfferingRepository;

    public List<CourseInSemesterDto> getCoursesBySemesterId(Integer semesterId) {
        List<CourseOffering> offerings = courseOfferingRepository.findBySemesterId(semesterId);

        return offerings.stream()
                .map(offering -> new CourseInSemesterDto(
                        offering.getCourseId(),
                        offering.getCourse().getCName(),
                        offering.getCourse().getCredits(),
                        offering.getMaxCapacity()
                ))
                .toList();
    }
}