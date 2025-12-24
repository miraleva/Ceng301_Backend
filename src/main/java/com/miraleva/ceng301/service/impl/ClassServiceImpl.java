package com.miraleva.ceng301.service.impl;

import com.miraleva.ceng301.dto.ClassDto;
import com.miraleva.ceng301.repository.ClassRepository;
import com.miraleva.ceng301.service.ClassService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ClassServiceImpl implements ClassService {

    private final ClassRepository classRepository;

    public ClassServiceImpl(ClassRepository classRepository) {
        this.classRepository = classRepository;
    }

    @Override
    public List<ClassDto> findAll() {
        return Collections.emptyList();
    }

    @Override
    public Optional<ClassDto> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public ClassDto create(ClassDto classDto) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public ClassDto update(Long id, ClassDto classDto) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void delete(Long id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
