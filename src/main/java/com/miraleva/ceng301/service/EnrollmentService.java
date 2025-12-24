package com.miraleva.ceng301.service;

import com.miraleva.ceng301.dto.EnrollmentDto;
import java.util.List;
import java.util.Optional;

public interface EnrollmentService {
    List<EnrollmentDto> findAll();

    Optional<EnrollmentDto> findById(Long id);

    EnrollmentDto create(EnrollmentDto enrollmentDto);

    EnrollmentDto update(Long id, EnrollmentDto enrollmentDto);

    void delete(Long id);
}
