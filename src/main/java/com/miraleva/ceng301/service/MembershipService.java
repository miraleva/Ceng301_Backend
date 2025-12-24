package com.miraleva.ceng301.service;

import com.miraleva.ceng301.dto.MembershipDto;
import java.util.List;
import java.util.Optional;

public interface MembershipService {
    List<MembershipDto> findAll();

    Optional<MembershipDto> findById(Long id);

    MembershipDto create(MembershipDto membershipDto);

    MembershipDto update(Long id, MembershipDto membershipDto);

    void delete(Long id);
}
