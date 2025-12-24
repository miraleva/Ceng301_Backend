package com.miraleva.ceng301.controller;

import com.miraleva.ceng301.dto.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @GetMapping("/oldest-member")
    public ApiResponse<String> getOldestMember() {
        return ApiResponse.success("Not implemented", null);
    }

    @GetMapping("/most-popular-class")
    public ApiResponse<String> getMostPopularClass() {
        return ApiResponse.success("Not implemented", null);
    }

    @GetMapping("/monthly-revenue")
    public ApiResponse<String> getMonthlyRevenue() {
        return ApiResponse.success("Not implemented", null);
    }

    @GetMapping("/members-by-membership")
    public ApiResponse<String> getMembersByMembership() {
        return ApiResponse.success("Not implemented", null);
    }

    @GetMapping("/trainer-workload")
    public ApiResponse<String> getTrainerWorkload() {
        return ApiResponse.success("Not implemented", null);
    }
}
