package com.miraleva.ceng301.service;

import com.miraleva.ceng301.dto.TrainerDto;
import java.util.List;
import java.util.Optional;

public interface TrainerService {
    List<TrainerDto> findAll();

    Optional<TrainerDto> findById(Long id);

    TrainerDto create(TrainerDto trainerDto);

    TrainerDto update(Long id, TrainerDto trainerDto);

    void delete(Long id);
}
