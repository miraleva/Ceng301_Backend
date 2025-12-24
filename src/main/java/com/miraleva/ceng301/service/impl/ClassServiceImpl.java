package com.miraleva.ceng301.service.impl;

import com.miraleva.ceng301.dto.ClassResponse;
import com.miraleva.ceng301.dto.ClassCreateRequest;
import com.miraleva.ceng301.dto.ClassUpdateRequest;
import com.miraleva.ceng301.dto.mapper.ClassMapper;
import com.miraleva.ceng301.repository.ClassRepository;
import com.miraleva.ceng301.service.ClassService;
import com.miraleva.ceng301.entity.ClassEntity;
import com.miraleva.ceng301.entity.TrainerEntity;
import com.miraleva.ceng301.repository.TrainerRepository;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClassServiceImpl implements ClassService {

    private final ClassRepository classRepository;
    private final TrainerRepository trainerRepository;

    public ClassServiceImpl(ClassRepository classRepository,
            TrainerRepository trainerRepository) {
        this.classRepository = classRepository;
        this.trainerRepository = trainerRepository;
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
        if (request.getClassName() == null || request.getSchedule() == null ||
                request.getCapacity() == null || request.getTrainerId() == null) {
            throw new IllegalArgumentException("Class Name, Schedule, Capacity, and Trainer ID are required");
        }

        if (request.getCapacity() <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0");
        }

        TrainerEntity trainer = trainerRepository.findById(request.getTrainerId())
                .orElseThrow(() -> new IllegalArgumentException("Trainer not found: " + request.getTrainerId()));

        ClassEntity entity = new ClassEntity();
        entity.setClassName(request.getClassName());
        entity.setSchedule(request.getSchedule());
        entity.setCapacity(request.getCapacity());
        entity.setTrainer(trainer);

        return ClassMapper.toResponse(classRepository.save(entity));
    }

    @Override
    public ClassResponse update(Integer id, ClassUpdateRequest request) {
        ClassEntity entity = classRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Class not found: " + id));

        if (request.getClassName() != null)
            entity.setClassName(request.getClassName());

        // Handle String to LocalDate conversion for update if needed,
        // OR better: Update ClassUpdateRequest to use LocalDate (Preferred).
        // Since ClassUpdateRequest uses String currently, we need to parse it.
        // But for consistency with CreateRequest, we SHOULD update ClassUpdateRequest
        // to LocalDate.
        // Assuming user will follow up with DTO fix, implementing parsing for now as
        // fail-safe or LocalDate if updated.
        // Waiting for user instruction on changing ClassUpdateRequest?
        // User said "schedule is LocalDate". I should fix DTO or parse.
        // The file I saw uses String schedule. I'll parse it here to avoid blocking
        // step D.

        if (request.getSchedule() != null) {
            entity.setSchedule(LocalDate.parse(request.getSchedule()));
        }

        if (request.getCapacity() != null) {
            if (request.getCapacity() <= 0)
                throw new IllegalArgumentException("Capacity must be greater than 0");
            entity.setCapacity(request.getCapacity());
        }

        if (request.getTrainerId() != null) {
            TrainerEntity trainer = trainerRepository.findById(request.getTrainerId())
                    .orElseThrow(() -> new IllegalArgumentException("Trainer not found: " + request.getTrainerId()));
            entity.setTrainer(trainer);
        }

        return ClassMapper.toResponse(classRepository.save(entity));
    }

    @Override
    public void delete(Integer id) {
        if (!classRepository.existsById(id)) {
            throw new IllegalArgumentException("Class not found: " + id);
        }
        try {
            classRepository.deleteById(id);
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            throw new org.springframework.dao.DataIntegrityViolationException(
                    "Cannot delete class: it has active enrollments", e);
        }
    }
}
