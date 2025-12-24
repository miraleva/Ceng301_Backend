package com.miraleva.ceng301.controller;

import com.miraleva.ceng301.dto.ApiResponse;
import com.miraleva.ceng301.dto.TrainerDto;
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
    public ApiResponse<List<TrainerDto>> getAll() {
        return ApiResponse.success("Fetched successfully", trainerService.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<TrainerDto> getById(@PathVariable Long id) {
        return ApiResponse.success("Fetched successfully", trainerService.findById(id).orElse(null));
    }

    @PostMapping
    public ApiResponse<TrainerDto> create(@RequestBody TrainerDto trainerDto) {
        return ApiResponse.success("Created successfully", trainerService.create(trainerDto));
    }

    @PutMapping("/{id}")
    public ApiResponse<TrainerDto> update(@PathVariable Long id, @RequestBody TrainerDto trainerDto) {
        return ApiResponse.success("Updated successfully", trainerService.update(id, trainerDto));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        trainerService.delete(id);
        return ApiResponse.success("Deleted successfully", null);
    }
}
