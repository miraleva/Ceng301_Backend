package com.miraleva.ceng301.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class PaymentResponse {
    private Integer paymentId;
    private LocalDate paymentDate;
    private BigDecimal amount;
    private String paymentMethod;
    private Integer memberId;

    public PaymentResponse(Integer paymentId, LocalDate paymentDate, BigDecimal amount, String paymentMethod,
            Integer memberId) {
        this.paymentId = paymentId;
        this.paymentDate = paymentDate;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.memberId = memberId;
    }

    // Getters
    public Integer getPaymentId() {
        return paymentId;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public Integer getMemberId() {
        return memberId;
    }
}
