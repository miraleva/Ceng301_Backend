package com.miraleva.ceng301.dto;

import java.math.BigDecimal;

public class MembershipResponse {
    private Integer membershipId;
    private String type;
    private BigDecimal price;
    private Integer duration;

    public MembershipResponse(Integer membershipId, String type, BigDecimal price, Integer duration) {
        this.membershipId = membershipId;
        this.type = type;
        this.price = price;
        this.duration = duration;
    }

    // Getters
    public Integer getMembershipId() {
        return membershipId;
    }

    public String getType() {
        return type;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getDuration() {
        return duration;
    }
}
