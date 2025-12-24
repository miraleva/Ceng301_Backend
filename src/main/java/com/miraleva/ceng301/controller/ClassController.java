package com.miraleva.ceng301.controller;

import com.miraleva.ceng301.dto.ApiResponse;
import com.miraleva.ceng301.dto.ClassResponse;
import com.miraleva.ceng301.dto.ClassCreateRequest;
import com.miraleva.ceng301.dto.ClassUpdateRequest;
import com.miraleva.ceng301.service.ClassService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
public class ClassController {

    private final ClassService classService;

    public ClassController(ClassService classService) {
        this.classService = classService;
    }

    @GetMapping
    public ApiResponse<List<ClassResponse>> getAll() {
        return ApiResponse.success("Fetched successfully", classService.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<ClassResponse> getById(@PathVariable Integer id) {
        return ApiResponse.success("Fetched successfully", classService.findById(id).orElse(null));
    }

    @PostMapping
    public ApiResponse<ClassResponse> create(@RequestBody ClassCreateRequest request) {
        return ApiResponse.success("Created successfully", classService.create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<ClassResponse> update(@PathVariable Integer id, @RequestBody ClassUpdateRequest request) {
        return ApiResponse.success("Updated successfully", classService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Integer id) {
        classService.delete(id);
        return ApiResponse.success("Deleted successfully", null);
    }
}
