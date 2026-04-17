package com.UniX.services;

import org.springframework.stereotype.Service;
import com.UniX.dtos.SemesterDto;
import com.UniX.repositories.SemesterRepository;
import lombok.AllArgsConstructor;
import java.util.List;

@Service
@AllArgsConstructor
public class SemesterService {
    private final SemesterRepository semesterRepository;

    public List<SemesterDto> getSemesters() {
        return semesterRepository.findByOpenForEnrolmentTrue().stream()
                .map(semester -> new SemesterDto(
                        semester.getSemesterId(),
                        semester.getSemester(),
                        semester.getYear()
                ))
                .toList();
    }
}
