package com.miraleva.ceng301.service;

import com.miraleva.ceng301.dto.ClassResponse;
import com.miraleva.ceng301.dto.ClassCreateRequest;
import com.miraleva.ceng301.dto.ClassUpdateRequest;
import java.util.List;
import java.util.Optional;

public interface ClassService {
    List<ClassResponse> findAll();

    Optional<ClassResponse> findById(Integer id);

    ClassResponse create(ClassCreateRequest request);

    ClassResponse update(Integer id, ClassUpdateRequest request);

    void delete(Integer id);
}
