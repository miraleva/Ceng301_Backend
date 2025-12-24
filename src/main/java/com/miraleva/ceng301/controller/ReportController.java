package com.miraleva.ceng301.controller;

import com.miraleva.ceng301.dto.ApiResponse;
import com.miraleva.ceng301.dto.report.*;
import com.miraleva.ceng301.service.ReportService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/oldest-member")
    public ApiResponse<OldestMemberReport> getOldestMember() {
        return ApiResponse.success("Fetched successfully", reportService.getOldestMember());
    }

    @GetMapping("/most-popular-class")
    public ApiResponse<MostPopularClassReport> getMostPopularClass() {
        return ApiResponse.success("Fetched successfully", reportService.getMostPopularClass());
    }

    @GetMapping("/monthly-revenue")
    public ApiResponse<MonthlyRevenueReport> getMonthlyRevenue(
            @RequestParam Integer year,
            @RequestParam Integer month) {
        return ApiResponse.success("Fetched successfully", reportService.getMonthlyRevenue(year, month));
    }

    @GetMapping("/members-by-membership")
    public ApiResponse<List<MembersByMembershipReport>> getMembersByMembership() {
        return ApiResponse.success("Fetched successfully", reportService.getMembersByMembership());
    }

    @GetMapping("/trainer-workload")
    public ApiResponse<List<TrainerWorkloadReport>> getTrainerWorkload() {
        return ApiResponse.success("Fetched successfully", reportService.getTrainerWorkload());
    }

    @GetMapping("/member-payment-summary")
    public ApiResponse<MemberPaymentSummaryReport> getMemberPaymentSummary(@RequestParam Integer memberId) {
        return ApiResponse.success("Fetched successfully", reportService.getMemberPaymentSummary(memberId));
    }
}
