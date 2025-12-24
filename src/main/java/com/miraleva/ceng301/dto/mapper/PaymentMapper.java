package com.miraleva.ceng301.dto.mapper;

import com.miraleva.ceng301.dto.PaymentResponse;
import com.miraleva.ceng301.entity.PaymentEntity;

public class PaymentMapper {
    public static PaymentResponse toResponse(PaymentEntity entity) {
        if (entity == null)
            return null;
        Integer memberId = null;
        String memberName = null;

        if (entity.getMember() != null) {
            memberId = entity.getMember().getMemberId();
            memberName = entity.getMember().getFirstName() + " " + entity.getMember().getLastName();
        }

        return new PaymentResponse(
                entity.getPaymentId(),
                entity.getPaymentDate(),
                entity.getAmount(),
                entity.getPaymentMethod(),
                memberId,
                memberName);
    }
}
