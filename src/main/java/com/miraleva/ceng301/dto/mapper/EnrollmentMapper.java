package com.miraleva.ceng301.dto.mapper;

import com.miraleva.ceng301.dto.EnrollmentResponse;
import com.miraleva.ceng301.entity.EnrollmentEntity;

public class EnrollmentMapper {
    public static EnrollmentResponse toResponse(EnrollmentEntity entity) {
        if (entity == null)
            return null;
        Integer memberId = (entity.getMember() != null) ? entity.getMember().getMemberId() : null;
        Integer classId = (entity.getGymClass() != null) ? entity.getGymClass().getClassId() : null;

        return new EnrollmentResponse(
                entity.getEnrollmentId(),
                memberId,
                classId,
                entity.getEnrollmentDate());
    }
}
