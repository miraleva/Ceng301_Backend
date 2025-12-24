package com.miraleva.ceng301.dto;

public class ClassResponse {
    private Integer classId;
    private String className;
    private String schedule;
    private Integer trainerId;

    public ClassResponse(Integer classId, String className, String schedule, Integer trainerId) {
        this.classId = classId;
        this.className = className;
        this.schedule = schedule;
        this.trainerId = trainerId;
    }

    // Getters
    public Integer getClassId() {
        return classId;
    }

    public String getClassName() {
        return className;
    }

    public String getSchedule() {
        return schedule;
    }

    public Integer getTrainerId() {
        return trainerId;
    }
}
