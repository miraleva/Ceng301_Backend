package com.miraleva.ceng301.service.impl;

import com.miraleva.ceng301.dto.PaymentDto;
import com.miraleva.ceng301.repository.PaymentRepository;
import com.miraleva.ceng301.service.PaymentService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public List<PaymentDto> findAll() {
        return Collections.emptyList();
    }

    @Override
    public Optional<PaymentDto> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public PaymentDto create(PaymentDto paymentDto) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public PaymentDto update(Long id, PaymentDto paymentDto) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void delete(Long id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
