package com.miraleva.ceng301.controller;

import com.miraleva.ceng301.dto.ApiResponse;
import com.miraleva.ceng301.dto.PaymentResponse;
import com.miraleva.ceng301.dto.PaymentCreateRequest;
import com.miraleva.ceng301.dto.PaymentUpdateRequest;
import com.miraleva.ceng301.service.PaymentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @GetMapping
    public ApiResponse<List<PaymentResponse>> getAll() {
        return ApiResponse.success("Fetched successfully", paymentService.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<PaymentResponse> getById(@PathVariable Integer id) {
        return ApiResponse.success("Fetched successfully", paymentService.findById(id).orElse(null));
    }

    @PostMapping
    public ApiResponse<PaymentResponse> create(@RequestBody PaymentCreateRequest request) {
        return ApiResponse.success("Created successfully", paymentService.create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<PaymentResponse> update(@PathVariable Integer id, @RequestBody PaymentUpdateRequest request) {
        return ApiResponse.success("Updated successfully", paymentService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Integer id) {
        paymentService.delete(id);
        return ApiResponse.success("Deleted successfully", null);
    }
}
