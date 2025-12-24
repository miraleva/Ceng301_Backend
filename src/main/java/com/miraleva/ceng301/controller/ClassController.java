package com.miraleva.ceng301.controller;

import com.miraleva.ceng301.dto.ApiResponse;
import com.miraleva.ceng301.dto.ClassDto;
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
    public ApiResponse<List<ClassDto>> getAll() {
        return ApiResponse.success("Fetched successfully", classService.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<ClassDto> getById(@PathVariable Long id) {
        return ApiResponse.success("Fetched successfully", classService.findById(id).orElse(null));
    }

    @PostMapping
    public ApiResponse<ClassDto> create(@RequestBody ClassDto classDto) {
        return ApiResponse.success("Created successfully", classService.create(classDto));
    }

    @PutMapping("/{id}")
    public ApiResponse<ClassDto> update(@PathVariable Long id, @RequestBody ClassDto classDto) {
        return ApiResponse.success("Updated successfully", classService.update(id, classDto));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        classService.delete(id);
        return ApiResponse.success("Deleted successfully", null);
    }
}
