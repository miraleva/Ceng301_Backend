package com.miraleva.ceng301.controller;

import com.miraleva.ceng301.dto.ApiResponse;
import com.miraleva.ceng301.dto.PaymentDto;
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
    public ApiResponse<List<PaymentDto>> getAll() {
        return ApiResponse.success("Fetched successfully", paymentService.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<PaymentDto> getById(@PathVariable Long id) {
        return ApiResponse.success("Fetched successfully", paymentService.findById(id).orElse(null));
    }

    @PostMapping
    public ApiResponse<PaymentDto> create(@RequestBody PaymentDto paymentDto) {
        return ApiResponse.success("Created successfully", paymentService.create(paymentDto));
    }

    @PutMapping("/{id}")
    public ApiResponse<PaymentDto> update(@PathVariable Long id, @RequestBody PaymentDto paymentDto) {
        return ApiResponse.success("Updated successfully", paymentService.update(id, paymentDto));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        paymentService.delete(id);
        return ApiResponse.success("Deleted successfully", null);
    }
}
