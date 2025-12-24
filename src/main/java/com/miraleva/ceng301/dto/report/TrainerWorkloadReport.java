package com.miraleva.ceng301.dto.report;

public class TrainerWorkloadReport {
    private Integer trainerId;
    private String firstName;
    private String lastName;
    private Long classCount;
    private Long totalEnrollments;

    // Getters and Setters
    public Integer getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Integer trainerId) {
        this.trainerId = trainerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getClassCount() {
        return classCount;
    }

    public void setClassCount(Long classCount) {
        this.classCount = classCount;
    }

    public Long getTotalEnrollments() {
        return totalEnrollments;
    }

    public void setTotalEnrollments(Long totalEnrollments) {
        this.totalEnrollments = totalEnrollments;
    }
}
