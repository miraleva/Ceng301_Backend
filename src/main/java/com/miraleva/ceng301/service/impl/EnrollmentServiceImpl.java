package com.miraleva.ceng301.service.impl;

import com.miraleva.ceng301.dto.EnrollmentResponse;
import com.miraleva.ceng301.dto.EnrollmentCreateRequest;
import com.miraleva.ceng301.dto.EnrollmentUpdateRequest;
import com.miraleva.ceng301.dto.mapper.EnrollmentMapper;
import com.miraleva.ceng301.repository.EnrollmentRepository;
import com.miraleva.ceng301.service.EnrollmentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository) {
        this.enrollmentRepository = enrollmentRepository;
    }

    @Override
    public List<EnrollmentResponse> findAll() {
        return enrollmentRepository.findAll().stream()
                .map(EnrollmentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<EnrollmentResponse> findById(Integer id) {
        return enrollmentRepository.findById(id)
                .map(EnrollmentMapper::toResponse);
    }

    @Override
    public EnrollmentResponse create(EnrollmentCreateRequest request) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public EnrollmentResponse update(Integer id, EnrollmentUpdateRequest request) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void delete(Integer id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
