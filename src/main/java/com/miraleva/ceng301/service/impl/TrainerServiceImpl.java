package com.miraleva.ceng301.service.impl;

import com.miraleva.ceng301.dto.TrainerResponse;
import com.miraleva.ceng301.dto.TrainerCreateRequest;
import com.miraleva.ceng301.dto.TrainerUpdateRequest;
import com.miraleva.ceng301.dto.mapper.TrainerMapper;
import com.miraleva.ceng301.repository.TrainerRepository;
import com.miraleva.ceng301.service.TrainerService;
import com.miraleva.ceng301.entity.TrainerEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TrainerServiceImpl implements TrainerService {

    private final TrainerRepository trainerRepository;

    public TrainerServiceImpl(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    @Override
    public List<TrainerResponse> findAll() {
        return trainerRepository.findAll().stream()
                .map(TrainerMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<TrainerResponse> findById(Integer id) {
        return trainerRepository.findById(id)
                .map(TrainerMapper::toResponse);
    }

    @Override
    public TrainerResponse create(TrainerCreateRequest request) {
        if (request.getFirstName() == null || request.getLastName() == null || request.getPhone() == null) {
            throw new IllegalArgumentException("First Name, Last Name, and Phone are required");
        }

        TrainerEntity entity = new TrainerEntity();
        entity.setFirstName(request.getFirstName());
        entity.setLastName(request.getLastName());
        entity.setPhone(request.getPhone());
        entity.setEmail(request.getEmail());
        entity.setSpecialization(request.getSpecialization());

        return TrainerMapper.toResponse(trainerRepository.save(entity));
    }

    @Override
    public TrainerResponse update(Integer id, TrainerUpdateRequest request) {
        TrainerEntity entity = trainerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Trainer not found: " + id));

        if (request.getFirstName() != null)
            entity.setFirstName(request.getFirstName());
        if (request.getLastName() != null)
            entity.setLastName(request.getLastName());
        if (request.getPhone() != null)
            entity.setPhone(request.getPhone());
        if (request.getEmail() != null)
            entity.setEmail(request.getEmail());
        if (request.getSpecialization() != null)
            entity.setSpecialization(request.getSpecialization());

        return TrainerMapper.toResponse(trainerRepository.save(entity));
    }

    @Override
    public void delete(Integer id) {
        if (!trainerRepository.existsById(id)) {
            throw new IllegalArgumentException("Trainer not found: " + id);
        }
        try {
            trainerRepository.deleteById(id);
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            throw new org.springframework.dao.DataIntegrityViolationException(
                    "Cannot delete trainer: they are assigned to existing classes", e);
        }
    }
}
