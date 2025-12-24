package com.miraleva.ceng301.service.impl;

import com.miraleva.ceng301.dto.report.*;
import com.miraleva.ceng301.service.ReportService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ReportServiceImpl implements ReportService {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ReportServiceImpl(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public OldestMemberReport getOldestMember() {
        String sql = """
                    SELECT
                        member_id AS memberId,
                        first_name AS firstName,
                        last_name AS lastName,
                        date_of_birth AS dateOfBirth,
                        CAST(EXTRACT(YEAR FROM AGE(CURRENT_DATE, date_of_birth)) AS INTEGER) AS ageYears
                    FROM
                        member
                    ORDER BY
                        date_of_birth ASC
                    LIMIT 1
                """;
        try {
            return jdbcTemplate.queryForObject(sql, new MapSqlParameterSource(),
                    new BeanPropertyRowMapper<>(OldestMemberReport.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public MostPopularClassReport getMostPopularClass() {
        String sql = """
                    SELECT
                        c.class_id AS classId,
                        c.class_name AS className,
                        COUNT(ce.enrollment_id) AS enrollmentCount
                    FROM
                        gym_class c
                    LEFT JOIN
                        class_enrollment ce ON c.class_id = ce.class_id
                    GROUP BY
                        c.class_id, c.class_name
                    ORDER BY
                        enrollmentCount DESC
                    LIMIT 1
                """;
        try {
            return jdbcTemplate.queryForObject(sql, new MapSqlParameterSource(),
                    new BeanPropertyRowMapper<>(MostPopularClassReport.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public MonthlyRevenueReport getMonthlyRevenue(Integer year, Integer month) {
        String sql = """
                    SELECT
                        CAST(EXTRACT(YEAR FROM payment_date) AS INTEGER) AS year,
                        CAST(EXTRACT(MONTH FROM payment_date) AS INTEGER) AS month,
                        COALESCE(SUM(amount), 0.00) AS totalRevenue
                    FROM
                        payment
                    WHERE
                        EXTRACT(YEAR FROM payment_date) = :year
                        AND EXTRACT(MONTH FROM payment_date) = :month
                    GROUP BY
                        EXTRACT(YEAR FROM payment_date),
                        EXTRACT(MONTH FROM payment_date)
                """;

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("year", year);
        params.addValue("month", month);

        try {
            return jdbcTemplate.queryForObject(sql, params,
                    new BeanPropertyRowMapper<>(MonthlyRevenueReport.class));
        } catch (EmptyResultDataAccessException e) {
            MonthlyRevenueReport empty = new MonthlyRevenueReport();
            empty.setYear(year);
            empty.setMonth(month);
            empty.setTotalRevenue(BigDecimal.ZERO);
            return empty;
        }
    }

    @Override
    public List<MembersByMembershipReport> getMembersByMembership() {
        String sql = """
                    SELECT
                        ms.membership_id AS membershipId,
                        ms.type AS type,
                        COUNT(m.member_id) AS memberCount
                    FROM
                        membership ms
                    LEFT JOIN
                        member m ON ms.membership_id = m.membership_id
                    GROUP BY
                        ms.membership_id, ms.type
                    ORDER BY
                        memberCount DESC
                """;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(MembersByMembershipReport.class));
    }

    @Override
    public List<TrainerWorkloadReport> getTrainerWorkload() {
        String sql = """
                    SELECT
                        t.trainer_id AS trainerId,
                        t.first_name AS firstName,
                        t.last_name AS lastName,
                        COUNT(DISTINCT c.class_id) AS classCount,
                        COUNT(ce.enrollment_id) AS totalEnrollments
                    FROM
                        trainer t
                    LEFT JOIN
                        gym_class c ON t.trainer_id = c.trainer_id
                    LEFT JOIN
                        class_enrollment ce ON c.class_id = ce.class_id
                    GROUP BY
                        t.trainer_id, t.first_name, t.last_name
                    ORDER BY
                        totalEnrollments DESC
                """;
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(TrainerWorkloadReport.class));
    }

    @Override
    public MemberPaymentSummaryReport getMemberPaymentSummary(Integer memberId) {

        // The function returns snake_case columns, we need to alias them for
        // BeanPropertyRowMapper
        String sqlAliased = """
                    SELECT
                        m.member_id AS memberId,
                        (m.first_name || ' ' || m.last_name) AS fullName,
                        COALESCE(SUM(p.amount), 0) AS totalPaid,
                        COUNT(p.payment_id) AS paymentCount,
                        MAX(p.payment_date) AS lastPaymentDate
                    FROM
                        member m
                    LEFT JOIN
                        payment p ON p.member_id = m.member_id
                    WHERE
                        m.member_id = :memberId
                    GROUP BY
                        m.member_id, m.first_name, m.last_name
                """;

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("memberId", memberId);

        try {
            return jdbcTemplate.queryForObject(sqlAliased, params,
                    new BeanPropertyRowMapper<>(MemberPaymentSummaryReport.class));
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
