package com.miraleva.ceng301.service;

import com.miraleva.ceng301.dto.ClassDto;
import java.util.List;
import java.util.Optional;

public interface ClassService {
    List<ClassDto> findAll();

    Optional<ClassDto> findById(Long id);

    ClassDto create(ClassDto classDto);

    ClassDto update(Long id, ClassDto classDto);

    void delete(Long id);
}
