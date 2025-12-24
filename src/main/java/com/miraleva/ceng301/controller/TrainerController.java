package com.miraleva.ceng301.controller;

import com.miraleva.ceng301.dto.ApiResponse;
import com.miraleva.ceng301.dto.TrainerResponse;
import com.miraleva.ceng301.dto.TrainerCreateRequest;
import com.miraleva.ceng301.dto.TrainerUpdateRequest;
import com.miraleva.ceng301.service.TrainerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trainers")
public class TrainerController {

    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @GetMapping
    public ApiResponse<List<TrainerResponse>> getAll() {
        return ApiResponse.success("Fetched successfully", trainerService.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<TrainerResponse> getById(@PathVariable Integer id) {
        return ApiResponse.success("Fetched successfully", trainerService.findById(id).orElse(null));
    }

    @PostMapping
    public ApiResponse<TrainerResponse> create(@RequestBody TrainerCreateRequest request) {
        return ApiResponse.success("Created successfully", trainerService.create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<TrainerResponse> update(@PathVariable Integer id, @RequestBody TrainerUpdateRequest request) {
        return ApiResponse.success("Updated successfully", trainerService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Integer id) {
        trainerService.delete(id);
        return ApiResponse.success("Deleted successfully", null);
    }
}
