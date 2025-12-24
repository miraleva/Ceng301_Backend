package com.miraleva.ceng301.dto.mapper;

import com.miraleva.ceng301.dto.ClassResponse;
import com.miraleva.ceng301.entity.ClassEntity;

public class ClassMapper {
    public static ClassResponse toResponse(ClassEntity entity) {
        if (entity == null)
            return null;
        Integer trainerId = null;
        String trainerName = null;

        if (entity.getTrainer() != null) {
            trainerId = entity.getTrainer().getTrainerId();
            trainerName = entity.getTrainer().getFirstName() + " " + entity.getTrainer().getLastName();
        }

        return new ClassResponse(
                entity.getClassId(),
                entity.getClassName(),
                entity.getSchedule(),
                entity.getCapacity(),
                trainerId,
                trainerName);
    }
}
