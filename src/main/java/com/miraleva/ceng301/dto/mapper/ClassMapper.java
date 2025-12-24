package com.miraleva.ceng301.dto.mapper;

import com.miraleva.ceng301.dto.ClassResponse;
import com.miraleva.ceng301.entity.ClassEntity;

public class ClassMapper {
    public static ClassResponse toResponse(ClassEntity entity) {
        if (entity == null)
            return null;
        Integer trainerId = (entity.getTrainer() != null) ? entity.getTrainer().getTrainerId() : null;

        return new ClassResponse(
                entity.getClassId(),
                entity.getClassName(),
                entity.getSchedule(),
                trainerId);
    }
}
