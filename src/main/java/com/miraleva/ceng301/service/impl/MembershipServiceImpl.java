package com.miraleva.ceng301.service.impl;

import com.miraleva.ceng301.dto.MembershipDto;
import com.miraleva.ceng301.repository.MembershipRepository;
import com.miraleva.ceng301.service.MembershipService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class MembershipServiceImpl implements MembershipService {

    private final MembershipRepository membershipRepository;

    public MembershipServiceImpl(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }

    @Override
    public List<MembershipDto> findAll() {
        return Collections.emptyList();
    }

    @Override
    public Optional<MembershipDto> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public MembershipDto create(MembershipDto membershipDto) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public MembershipDto update(Long id, MembershipDto membershipDto) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void delete(Long id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
