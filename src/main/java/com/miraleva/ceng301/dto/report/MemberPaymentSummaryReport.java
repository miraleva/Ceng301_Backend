package com.miraleva.ceng301.dto.report;

import java.math.BigDecimal;
import java.time.LocalDate;

public class MemberPaymentSummaryReport {
    private Integer memberId;
    private String fullName;
    private BigDecimal totalPaid;
    private Long paymentCount;
    private LocalDate lastPaymentDate;

    // Getters and Setters
    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public BigDecimal getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(BigDecimal totalPaid) {
        this.totalPaid = totalPaid;
    }

    public Long getPaymentCount() {
        return paymentCount;
    }

    public void setPaymentCount(Long paymentCount) {
        this.paymentCount = paymentCount;
    }

    public LocalDate getLastPaymentDate() {
        return lastPaymentDate;
    }

    public void setLastPaymentDate(LocalDate lastPaymentDate) {
        this.lastPaymentDate = lastPaymentDate;
    }
}
