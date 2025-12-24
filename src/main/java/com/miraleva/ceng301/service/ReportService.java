package com.miraleva.ceng301.service;

import com.miraleva.ceng301.dto.report.*;

import java.util.List;

public interface ReportService {
    OldestMemberReport getOldestMember();

    MostPopularClassReport getMostPopularClass();

    MonthlyRevenueReport getMonthlyRevenue(Integer year, Integer month);

    List<MembersByMembershipReport> getMembersByMembership();

    List<TrainerWorkloadReport> getTrainerWorkload();

    MemberPaymentSummaryReport getMemberPaymentSummary(Integer memberId);
}
