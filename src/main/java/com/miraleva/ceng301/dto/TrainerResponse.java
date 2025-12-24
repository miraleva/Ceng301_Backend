package com.miraleva.ceng301.dto;

public class TrainerResponse {
    private Integer trainerId;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String specialization;

    public TrainerResponse(Integer trainerId, String firstName, String lastName, String phone, String email,
            String specialization) {
        this.trainerId = trainerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.specialization = specialization;
    }

    // Getters
    public Integer getTrainerId() {
        return trainerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getSpecialization() {
        return specialization;
    }
}
