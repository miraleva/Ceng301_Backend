package com.miraleva.ceng301.service.impl;

import com.miraleva.ceng301.dto.EnrollmentCreateRequest;
import com.miraleva.ceng301.dto.EnrollmentResponse;
import com.miraleva.ceng301.dto.EnrollmentUpdateRequest;
import com.miraleva.ceng301.entity.ClassEntity;
import com.miraleva.ceng301.entity.EnrollmentEntity;
import com.miraleva.ceng301.entity.MemberEntity;
import com.miraleva.ceng301.repository.ClassRepository;
import com.miraleva.ceng301.repository.EnrollmentRepository;
import com.miraleva.ceng301.repository.MemberRepository;
import com.miraleva.ceng301.service.EnrollmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final MemberRepository memberRepository;
    private final ClassRepository classRepository;

    public EnrollmentServiceImpl(
            EnrollmentRepository enrollmentRepository,
            MemberRepository memberRepository,
            ClassRepository classRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.memberRepository = memberRepository;
        this.classRepository = classRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<EnrollmentResponse> findAll() {
        return enrollmentRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EnrollmentResponse> findById(Integer id) {
        return enrollmentRepository.findById(id).map(this::toResponse);
    }

    @Override
    public EnrollmentResponse create(EnrollmentCreateRequest request) {
        // Validate request
        if (request.getMemberId() == null || request.getClassId() == null) {
            throw new IllegalArgumentException("memberId and classId are required");
        }

        MemberEntity member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("Member not found: " + request.getMemberId()));

        ClassEntity gymClass = classRepository.findById(request.getClassId())
                .orElseThrow(() -> new IllegalArgumentException("Class not found: " + request.getClassId()));

        EnrollmentEntity enrollment = new EnrollmentEntity();
        enrollment.setMember(member);
        enrollment.setGymClass(gymClass);

        // If client didn't send enrollmentDate default to today
        LocalDate date = (request.getEnrollmentDate() != null) ? request.getEnrollmentDate() : LocalDate.now();
        enrollment.setEnrollmentDate(date);

        EnrollmentEntity saved = enrollmentRepository.save(enrollment);
        return toResponse(saved);
    }

    @Override
    public EnrollmentResponse update(Integer id, EnrollmentUpdateRequest request) {
        EnrollmentEntity enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Enrollment not found: " + id));

        // Only update fields you allow
        if (request.getEnrollmentDate() != null) {
            enrollment.setEnrollmentDate(request.getEnrollmentDate());
        }

        EnrollmentEntity saved = enrollmentRepository.save(enrollment);
        return toResponse(saved);
    }

    @Override
    public void delete(Integer id) {
        if (!enrollmentRepository.existsById(id)) {
            throw new IllegalArgumentException("Enrollment not found: " + id);
        }
        enrollmentRepository.deleteById(id);
    }

    private EnrollmentResponse toResponse(EnrollmentEntity e) {
        String memberName = null;
        if (e.getMember() != null) {
            memberName = e.getMember().getFirstName() + " " + e.getMember().getLastName();
        }

        String className = null;
        if (e.getGymClass() != null) {
            className = e.getGymClass().getClassName();
        }

        EnrollmentResponse dto = new EnrollmentResponse();
        dto.setEnrollmentId(e.getEnrollmentId());

        if (e.getMember() != null) {
            dto.setMemberId(e.getMember().getMemberId());
        }

        dto.setMemberName(memberName);

        if (e.getGymClass() != null) {
            dto.setClassId(e.getGymClass().getClassId());
        }

        dto.setClassName(className);
        dto.setEnrollmentDate(e.getEnrollmentDate());
        return dto;
    }
}
