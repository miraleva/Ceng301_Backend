package com.miraleva.ceng301.service.impl;

import com.miraleva.ceng301.dto.MemberResponse;
import com.miraleva.ceng301.dto.MemberCreateRequest;
import com.miraleva.ceng301.dto.MemberUpdateRequest;
import com.miraleva.ceng301.dto.mapper.MemberMapper;
import com.miraleva.ceng301.repository.MemberRepository;
import com.miraleva.ceng301.service.MemberService;
import com.miraleva.ceng301.entity.MemberEntity;
import com.miraleva.ceng301.entity.MembershipEntity;
import com.miraleva.ceng301.repository.MembershipRepository;
import java.time.LocalDate;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MembershipRepository membershipRepository;

    public MemberServiceImpl(MemberRepository memberRepository,
            MembershipRepository membershipRepository) {
        this.memberRepository = memberRepository;
        this.membershipRepository = membershipRepository;
    }

    @Override
    public List<MemberResponse> findAll() {
        return memberRepository.findAll().stream()
                .map(MemberMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<MemberResponse> findById(Integer id) {
        return memberRepository.findById(id)
                .map(MemberMapper::toResponse);
    }

    @Override
    public MemberResponse create(MemberCreateRequest request) {
        if (request.getFirstName() == null || request.getLastName() == null ||
                request.getDateOfBirth() == null || request.getPhone() == null || request.getMembershipId() == null) {
            throw new IllegalArgumentException("First Name, Last Name, DOB, Phone, and Membership ID are required");
        }

        MembershipEntity membership = membershipRepository.findById(request.getMembershipId())
                .orElseThrow(() -> new IllegalArgumentException("Membership not found: " + request.getMembershipId()));

        MemberEntity entity = new MemberEntity();
        entity.setFirstName(request.getFirstName());
        entity.setLastName(request.getLastName());
        entity.setGender(request.getGender());
        entity.setDateOfBirth(request.getDateOfBirth());
        entity.setPhone(request.getPhone());
        entity.setEmail(request.getEmail());
        entity.setMembership(membership);
        entity.setRegistrationDate(LocalDate.now());

        return MemberMapper.toResponse(memberRepository.save(entity));
    }

    @Override
    public MemberResponse update(Integer id, MemberUpdateRequest request) {
        MemberEntity entity = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Member not found: " + id));

        if (request.getFirstName() != null)
            entity.setFirstName(request.getFirstName());
        if (request.getLastName() != null)
            entity.setLastName(request.getLastName());
        if (request.getGender() != null)
            entity.setGender(request.getGender());
        if (request.getDateOfBirth() != null)
            entity.setDateOfBirth(request.getDateOfBirth());
        if (request.getPhone() != null)
            entity.setPhone(request.getPhone());
        if (request.getEmail() != null)
            entity.setEmail(request.getEmail());

        if (request.getMembershipId() != null) {
            MembershipEntity membership = membershipRepository.findById(request.getMembershipId())
                    .orElseThrow(
                            () -> new IllegalArgumentException("Membership not found: " + request.getMembershipId()));
            entity.setMembership(membership);
        }

        return MemberMapper.toResponse(memberRepository.save(entity));
    }

    @Override
    public void delete(Integer id) {
        if (!memberRepository.existsById(id)) {
            throw new IllegalArgumentException("Member not found: " + id);
        }
        try {
            memberRepository.deleteById(id);
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            throw new org.springframework.dao.DataIntegrityViolationException(
                    "Cannot delete member: dependent records exist", e);
        }
    }
}
