package com.miraleva.ceng301.dto.mapper;

import com.miraleva.ceng301.dto.MemberResponse;
import com.miraleva.ceng301.entity.MemberEntity;

public class MemberMapper {
    public static MemberResponse toResponse(MemberEntity entity) {
        if (entity == null)
            return null;
        Integer membershipId = (entity.getMembership() != null) ? entity.getMembership().getMembershipId() : null;

        return new MemberResponse(
                entity.getMemberId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getGender(),
                entity.getDateOfBirth(),
                entity.getPhone(),
                entity.getEmail(),
                entity.getRegistrationDate(),
                membershipId);
    }
}
