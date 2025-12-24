package com.miraleva.ceng301.service.impl;

import com.miraleva.ceng301.dto.ClassResponse;
import com.miraleva.ceng301.dto.ClassCreateRequest;
import com.miraleva.ceng301.dto.ClassUpdateRequest;
import com.miraleva.ceng301.dto.mapper.ClassMapper;
import com.miraleva.ceng301.repository.ClassRepository;
import com.miraleva.ceng301.service.ClassService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClassServiceImpl implements ClassService {

    private final ClassRepository classRepository;

    public ClassServiceImpl(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    @Override
    public List<ClassResponse> findAll() {
        return classRepository.findAll().stream()
                .map(ClassMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ClassResponse> findById(Integer id) {
        return classRepository.findById(id)
                .map(ClassMapper::toResponse);
    }

    @Override
    public ClassResponse create(ClassCreateRequest request) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public ClassResponse update(Integer id, ClassUpdateRequest request) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void delete(Integer id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
