package com.miraleva.ceng301.dto;

import java.time.LocalDate;

public class EnrollmentResponse {
    private Integer enrollmentId;
    private Integer memberId;
    private String memberName;
    private Integer classId;
    private String className;
    private LocalDate enrollmentDate;

    public EnrollmentResponse(Integer enrollmentId, Integer memberId, String memberName, Integer classId,
            String className, LocalDate enrollmentDate) {
        this.enrollmentId = enrollmentId;
        this.memberId = memberId;
        this.memberName = memberName;
        this.classId = classId;
        this.className = className;
        this.enrollmentDate = enrollmentDate;
    }

    // Getters
    public Integer getEnrollmentId() {
        return enrollmentId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public Integer getClassId() {
        return classId;
    }

    public String getClassName() {
        return className;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }
}
