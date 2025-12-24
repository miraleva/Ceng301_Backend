package com.miraleva.ceng301.service.impl;

import com.miraleva.ceng301.dto.TrainerResponse;
import com.miraleva.ceng301.dto.TrainerCreateRequest;
import com.miraleva.ceng301.dto.TrainerUpdateRequest;
import com.miraleva.ceng301.dto.mapper.TrainerMapper;
import com.miraleva.ceng301.repository.TrainerRepository;
import com.miraleva.ceng301.service.TrainerService;
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
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public TrainerResponse update(Integer id, TrainerUpdateRequest request) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void delete(Integer id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
