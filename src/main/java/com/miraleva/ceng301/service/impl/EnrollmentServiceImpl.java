package com.miraleva.ceng301.service.impl;

import com.miraleva.ceng301.dto.EnrollmentDto;
import com.miraleva.ceng301.repository.EnrollmentRepository;
import com.miraleva.ceng301.service.EnrollmentService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public List<EnrollmentDto> findAll() {
        return Collections.emptyList();
    }

    @Override
    public Optional<EnrollmentDto> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public EnrollmentDto create(EnrollmentDto enrollmentDto) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public EnrollmentDto update(Long id, EnrollmentDto enrollmentDto) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void delete(Long id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
