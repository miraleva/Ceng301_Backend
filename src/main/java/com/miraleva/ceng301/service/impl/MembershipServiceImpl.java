package com.miraleva.ceng301.service.impl;

import com.miraleva.ceng301.dto.MembershipResponse;
import com.miraleva.ceng301.dto.MembershipCreateRequest;
import com.miraleva.ceng301.dto.MembershipUpdateRequest;
import com.miraleva.ceng301.dto.mapper.MembershipMapper;
import com.miraleva.ceng301.repository.MembershipRepository;
import com.miraleva.ceng301.service.MembershipService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MembershipServiceImpl implements MembershipService {

    private final MembershipRepository membershipRepository;

    public MembershipServiceImpl(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }

    @Override
    public List<MembershipResponse> findAll() {
        return membershipRepository.findAll().stream()
                .map(MembershipMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<MembershipResponse> findById(Integer id) {
        return membershipRepository.findById(id)
                .map(MembershipMapper::toResponse);
    }

    @Override
    public MembershipResponse create(MembershipCreateRequest request) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public MembershipResponse update(Integer id, MembershipUpdateRequest request) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void delete(Integer id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
