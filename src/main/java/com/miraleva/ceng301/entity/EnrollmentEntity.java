package com.miraleva.ceng301.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "class_enrollment", uniqueConstraints = {
        @UniqueConstraint(columnNames = { "member_id", "class_id" })
})
public class EnrollmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "enrollment_id")
    private Integer enrollmentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private MemberEntity member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "class_id", nullable = false)
    private ClassEntity gymClass;

    @Column(name = "enrollment_date")
    private LocalDate enrollmentDate;

    // Getters and Setters

    public Integer getEnrollmentId() {
        return enrollmentId;
    }

    public void setEnrollmentId(Integer enrollmentId) {
        this.enrollmentId = enrollmentId;
    }

    public MemberEntity getMember() {
        return member;
    }

    public void setMember(MemberEntity member) {
        this.member = member;
    }

    public ClassEntity getGymClass() {
        return gymClass;
    }

    public void setGymClass(ClassEntity gymClass) {
        this.gymClass = gymClass;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }
}
