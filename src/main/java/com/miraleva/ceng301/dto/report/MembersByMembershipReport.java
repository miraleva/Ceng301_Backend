package com.miraleva.ceng301.dto.report;

public class MembersByMembershipReport {
    private Integer membershipId;
    private String type;
    private Long memberCount;

    // Getters and Setters
    public Integer getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(Integer membershipId) {
        this.membershipId = membershipId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(Long memberCount) {
        this.memberCount = memberCount;
    }
}
