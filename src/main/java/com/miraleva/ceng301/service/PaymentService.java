package com.miraleva.ceng301.service;

import com.miraleva.ceng301.dto.PaymentDto;
import java.util.List;
import java.util.Optional;

public interface PaymentService {
    List<PaymentDto> findAll();

    Optional<PaymentDto> findById(Long id);

    PaymentDto create(PaymentDto paymentDto);

    PaymentDto update(Long id, PaymentDto paymentDto);

    void delete(Long id);
}
