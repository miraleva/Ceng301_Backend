package com.miraleva.ceng301.dto.mapper;

import com.miraleva.ceng301.dto.EnrollmentResponse;
import com.miraleva.ceng301.entity.EnrollmentEntity;

public class EnrollmentMapper {
    public static EnrollmentResponse toResponse(EnrollmentEntity entity) {
        if (entity == null)
            return null;
        Integer memberId = null;
        String memberName = null;
        Integer classId = null;
        String className = null;

        if (entity.getMember() != null) {
            memberId = entity.getMember().getMemberId();
            memberName = entity.getMember().getFirstName() + " " + entity.getMember().getLastName();
        }

        if (entity.getGymClass() != null) {
            classId = entity.getGymClass().getClassId();
            className = entity.getGymClass().getClassName();
        }

        return new EnrollmentResponse(
                entity.getEnrollmentId(),
                memberId,
                memberName,
                classId,
                className,
                entity.getEnrollmentDate());
    }
}
