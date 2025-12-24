package com.miraleva.ceng301.service;

import com.miraleva.ceng301.dto.EnrollmentResponse;
import com.miraleva.ceng301.dto.EnrollmentCreateRequest;
import com.miraleva.ceng301.dto.EnrollmentUpdateRequest;
import java.util.List;
import java.util.Optional;

public interface EnrollmentService {
    List<EnrollmentResponse> findAll();

    Optional<EnrollmentResponse> findById(Integer id);

    EnrollmentResponse create(EnrollmentCreateRequest request);

    EnrollmentResponse update(Integer id, EnrollmentUpdateRequest request);

    void delete(Integer id);
}
