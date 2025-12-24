package com.miraleva.ceng301.dto;

import java.time.LocalDate;

public class EnrollmentResponse {
    private Integer enrollmentId;
    private Integer memberId;
    private Integer classId;
    private LocalDate enrollmentDate;

    public EnrollmentResponse(Integer enrollmentId, Integer memberId, Integer classId, LocalDate enrollmentDate) {
        this.enrollmentId = enrollmentId;
        this.memberId = memberId;
        this.classId = classId;
        this.enrollmentDate = enrollmentDate;
    }

    // Getters
    public Integer getEnrollmentId() {
        return enrollmentId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public Integer getClassId() {
        return classId;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }
}
