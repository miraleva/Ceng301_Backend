package com.miraleva.ceng301.service.impl;

import com.miraleva.ceng301.dto.PaymentResponse;
import com.miraleva.ceng301.dto.PaymentCreateRequest;
import com.miraleva.ceng301.dto.PaymentUpdateRequest;
import com.miraleva.ceng301.dto.mapper.PaymentMapper;
import com.miraleva.ceng301.repository.PaymentRepository;
import com.miraleva.ceng301.service.PaymentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public List<PaymentResponse> findAll() {
        return paymentRepository.findAll().stream()
                .map(PaymentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PaymentResponse> findById(Integer id) {
        return paymentRepository.findById(id)
                .map(PaymentMapper::toResponse);
    }

    @Override
    public PaymentResponse create(PaymentCreateRequest request) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public PaymentResponse update(Integer id, PaymentUpdateRequest request) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void delete(Integer id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
