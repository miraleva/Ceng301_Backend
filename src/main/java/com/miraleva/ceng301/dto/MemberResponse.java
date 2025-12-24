package com.miraleva.ceng301.dto;

import java.time.LocalDate;

public class MemberResponse {
    private Integer memberId;
    private String firstName;
    private String lastName;
    private String gender;
    private LocalDate dateOfBirth;
    private String phone;
    private String email;
    private LocalDate registrationDate;
    private Integer membershipId;

    public MemberResponse(Integer memberId, String firstName, String lastName, String gender, LocalDate dateOfBirth,
            String phone, String email, LocalDate registrationDate, Integer membershipId) {
        this.memberId = memberId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.email = email;
        this.registrationDate = registrationDate;
        this.membershipId = membershipId;
    }

    // Getters
    public Integer getMemberId() {
        return memberId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public Integer getMembershipId() {
        return membershipId;
    }
}
