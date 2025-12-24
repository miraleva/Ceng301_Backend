package com.miraleva.ceng301.dto;

import java.time.LocalDate;

public class EnrollmentResponse {
    private Integer enrollmentId;
    private Integer memberId;
    private String memberName;
    private Integer classId;
    private String className;
    private LocalDate enrollmentDate;

    public EnrollmentResponse() {
    }

    public EnrollmentResponse(Integer enrollmentId, Integer memberId, String memberName, Integer classId,
            String className, LocalDate enrollmentDate) {
        this.enrollmentId = enrollmentId;
        this.memberId = memberId;
        this.memberName = memberName;
        this.classId = classId;
        this.className = className;
        this.enrollmentDate = enrollmentDate;
    }

    // Setters
    public void setEnrollmentId(Integer enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
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
