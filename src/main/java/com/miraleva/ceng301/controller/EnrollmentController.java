package com.miraleva.ceng301.controller;

import com.miraleva.ceng301.dto.ApiResponse;
import com.miraleva.ceng301.dto.EnrollmentDto;
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
    public ApiResponse<List<EnrollmentDto>> getAll() {
        return ApiResponse.success("Fetched successfully", enrollmentService.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<EnrollmentDto> getById(@PathVariable Long id) {
        return ApiResponse.success("Fetched successfully", enrollmentService.findById(id).orElse(null));
    }

    @PostMapping
    public ApiResponse<EnrollmentDto> create(@RequestBody EnrollmentDto enrollmentDto) {
        return ApiResponse.success("Created successfully", enrollmentService.create(enrollmentDto));
    }

    @PutMapping("/{id}")
    public ApiResponse<EnrollmentDto> update(@PathVariable Long id, @RequestBody EnrollmentDto enrollmentDto) {
        return ApiResponse.success("Updated successfully", enrollmentService.update(id, enrollmentDto));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        enrollmentService.delete(id);
        return ApiResponse.success("Deleted successfully", null);
    }
}
