package com.miraleva.ceng301.controller;

import com.miraleva.ceng301.dto.ApiResponse;
import com.miraleva.ceng301.dto.EnrollmentResponse;
import com.miraleva.ceng301.dto.EnrollmentCreateRequest;
import com.miraleva.ceng301.dto.EnrollmentUpdateRequest;
import com.miraleva.ceng301.service.EnrollmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @GetMapping
    public ApiResponse<List<EnrollmentResponse>> getAll() {
        return ApiResponse.success("Fetched successfully", enrollmentService.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<EnrollmentResponse> getById(@PathVariable Integer id) {
        return ApiResponse.success("Fetched successfully", enrollmentService.findById(id).orElse(null));
    }

    @PostMapping("/create")
    public ApiResponse<EnrollmentResponse> create(@RequestBody EnrollmentCreateRequest request) {
        return ApiResponse.success("Created successfully", enrollmentService.create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<EnrollmentResponse> update(@PathVariable Integer id,
            @RequestBody EnrollmentUpdateRequest request) {
        return ApiResponse.success("Updated successfully", enrollmentService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Integer id) {
        enrollmentService.delete(id);
        return ApiResponse.success("Deleted successfully", null);
    }
}
