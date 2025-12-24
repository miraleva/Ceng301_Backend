package com.miraleva.ceng301.service;

import com.miraleva.ceng301.dto.PaymentResponse;
import com.miraleva.ceng301.dto.PaymentCreateRequest;
import com.miraleva.ceng301.dto.PaymentUpdateRequest;
import java.util.List;
import java.util.Optional;

public interface PaymentService {
    List<PaymentResponse> findAll();

    Optional<PaymentResponse> findById(Integer id);

    PaymentResponse create(PaymentCreateRequest request);

    PaymentResponse update(Integer id, PaymentUpdateRequest request);

    void delete(Integer id);
}
