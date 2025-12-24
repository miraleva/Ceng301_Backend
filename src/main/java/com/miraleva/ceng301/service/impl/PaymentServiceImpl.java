package com.miraleva.ceng301.service.impl;

import com.miraleva.ceng301.dto.PaymentCreateRequest;
import com.miraleva.ceng301.dto.PaymentResponse;
import com.miraleva.ceng301.dto.PaymentUpdateRequest;
import com.miraleva.ceng301.dto.mapper.PaymentMapper;
import com.miraleva.ceng301.entity.MemberEntity;
import com.miraleva.ceng301.entity.PaymentEntity;
import com.miraleva.ceng301.repository.MemberRepository;
import com.miraleva.ceng301.repository.PaymentRepository;
import com.miraleva.ceng301.service.PaymentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final MemberRepository memberRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository, MemberRepository memberRepository) {
        this.paymentRepository = paymentRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaymentResponse> findAll() {
        return paymentRepository.findAll().stream()
                .map(PaymentMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PaymentResponse> findById(Integer id) {
        return paymentRepository.findById(id)
                .map(PaymentMapper::toResponse);
    }

    @Override
    public PaymentResponse create(PaymentCreateRequest request) {
        if (request.getMemberId() == null) {
            throw new IllegalArgumentException("memberId is required");
        }

        MemberEntity member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("Member not found: " + request.getMemberId()));

        PaymentEntity payment = new PaymentEntity();
        payment.setAmount(request.getAmount());
        payment.setPaymentMethod(request.getPaymentMethod());
        // Default to now if null
        payment.setPaymentDate(request.getPaymentDate() != null ? request.getPaymentDate() : LocalDate.now());
        payment.setMember(member);

        PaymentEntity saved = paymentRepository.save(payment);
        return PaymentMapper.toResponse(saved);
    }

    @Override
    public PaymentResponse update(Integer id, PaymentUpdateRequest request) {
        PaymentEntity payment = paymentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found: " + id));

        // Basic update logic - in real app might need more validation
        if (request.getAmount() != null) {
            payment.setAmount(request.getAmount());
        }
        if (request.getPaymentMethod() != null) {
            payment.setPaymentMethod(request.getPaymentMethod());
        }
        // Assuming update request doesn't have paymentDate for now, or add if needed.
        // If PaymentUpdateRequest has paymentDate, verify via DTO.

        PaymentEntity saved = paymentRepository.save(payment);
        return PaymentMapper.toResponse(saved);
    }

    @Override
    public void delete(Integer id) {
        if (!paymentRepository.existsById(id)) {
            throw new IllegalArgumentException("Payment not found: " + id);
        }
        paymentRepository.deleteById(id);
    }
}
