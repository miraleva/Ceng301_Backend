package com.miraleva.ceng301.dto.mapper;

import com.miraleva.ceng301.dto.MembershipResponse;
import com.miraleva.ceng301.entity.MembershipEntity;

public class MembershipMapper {
    public static MembershipResponse toResponse(MembershipEntity entity) {
        if (entity == null)
            return null;
        return new MembershipResponse(
                entity.getMembershipId(),
                entity.getType(),
                entity.getPrice(),
                entity.getDuration());
    }
}
