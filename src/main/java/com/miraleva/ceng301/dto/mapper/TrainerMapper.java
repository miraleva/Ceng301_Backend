package com.miraleva.ceng301.dto.mapper;

import com.miraleva.ceng301.dto.TrainerResponse;
import com.miraleva.ceng301.entity.TrainerEntity;

public class TrainerMapper {
    public static TrainerResponse toResponse(TrainerEntity entity) {
        if (entity == null)
            return null;
        return new TrainerResponse(
                entity.getTrainerId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getPhone(),
                entity.getEmail(),
                entity.getSpecialization());
    }
}
