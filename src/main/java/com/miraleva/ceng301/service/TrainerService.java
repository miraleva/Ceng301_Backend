package com.miraleva.ceng301.service;

import com.miraleva.ceng301.dto.TrainerResponse;
import com.miraleva.ceng301.dto.TrainerCreateRequest;
import com.miraleva.ceng301.dto.TrainerUpdateRequest;
import java.util.List;
import java.util.Optional;

public interface TrainerService {
    List<TrainerResponse> findAll();

    Optional<TrainerResponse> findById(Integer id);

    TrainerResponse create(TrainerCreateRequest request);

    TrainerResponse update(Integer id, TrainerUpdateRequest request);

    void delete(Integer id);
}
