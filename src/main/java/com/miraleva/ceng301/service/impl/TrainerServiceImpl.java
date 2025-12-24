package com.miraleva.ceng301.service.impl;

import com.miraleva.ceng301.dto.TrainerDto;
import com.miraleva.ceng301.repository.TrainerRepository;
import com.miraleva.ceng301.service.TrainerService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class TrainerServiceImpl implements TrainerService {

    private final TrainerRepository trainerRepository;

    public TrainerServiceImpl(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    @Override
    public List<TrainerDto> findAll() {
        return Collections.emptyList();
    }

    @Override
    public Optional<TrainerDto> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public TrainerDto create(TrainerDto trainerDto) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public TrainerDto update(Long id, TrainerDto trainerDto) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void delete(Long id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
