package com.miraleva.ceng301.service.impl;

import com.miraleva.ceng301.dto.MembershipResponse;
import com.miraleva.ceng301.dto.MembershipCreateRequest;
import com.miraleva.ceng301.dto.MembershipUpdateRequest;
import com.miraleva.ceng301.dto.mapper.MembershipMapper;
import com.miraleva.ceng301.repository.MembershipRepository;
import com.miraleva.ceng301.service.MembershipService;
import org.springframework.stereotype.Service;

import com.miraleva.ceng301.entity.MembershipEntity;
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
        if (request.getType() == null || request.getPrice() == null || request.getDuration() == null) {
            throw new IllegalArgumentException("Type, Price, and Duration are required");
        }

        // Enforce valid types if required by requirements, though DB check also exists
        if (!List.of("Gold", "Silver", "Platinum").contains(request.getType())) {
            throw new IllegalArgumentException("Invalid membership type. Allowed: Gold, Silver, Platinum");
        }

        MembershipEntity entity = new MembershipEntity();
        entity.setType(request.getType());
        entity.setPrice(request.getPrice());
        entity.setDuration(request.getDuration());

        return MembershipMapper.toResponse(membershipRepository.save(entity));
    }

    @Override
    public MembershipResponse update(Integer id, MembershipUpdateRequest request) {
        MembershipEntity entity = membershipRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Membership not found: " + id));

        if (request.getType() != null) {
            if (!List.of("Gold", "Silver", "Platinum").contains(request.getType())) {
                throw new IllegalArgumentException("Invalid membership type. Allowed: Gold, Silver, Platinum");
            }
            entity.setType(request.getType());
        }
        if (request.getPrice() != null)
            entity.setPrice(request.getPrice());
        if (request.getDuration() != null)
            entity.setDuration(request.getDuration());

        return MembershipMapper.toResponse(membershipRepository.save(entity));
    }

    @Override
    public void delete(Integer id) {
        if (!membershipRepository.existsById(id)) {
            throw new IllegalArgumentException("Membership not found: " + id);
        }
        try {
            membershipRepository.deleteById(id);
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            throw new org.springframework.dao.DataIntegrityViolationException(
                    "Cannot delete membership: it is referenced by existing members", e);
        }
    }
}
