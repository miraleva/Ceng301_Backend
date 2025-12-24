package com.miraleva.ceng301.dto;

import java.time.LocalDate;

public class ClassResponse {
    private Integer classId;
    private String className;
    private LocalDate schedule;
    private Integer capacity;
    private Integer trainerId;
    private String trainerName;

    public ClassResponse(Integer classId, String className, LocalDate schedule, Integer capacity, Integer trainerId,
            String trainerName) {
        this.classId = classId;
        this.className = className;
        this.schedule = schedule;
        this.capacity = capacity;
        this.trainerId = trainerId;
        this.trainerName = trainerName;
    }

    // Getters
    public Integer getClassId() {
        return classId;
    }

    public String getClassName() {
        return className;
    }

    public LocalDate getSchedule() {
        return schedule;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Integer getTrainerId() {
        return trainerId;
    }

    public String getTrainerName() {
        return trainerName;
    }
}
