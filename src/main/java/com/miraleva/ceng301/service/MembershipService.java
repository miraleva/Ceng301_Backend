package com.miraleva.ceng301.service;

import com.miraleva.ceng301.dto.MembershipResponse;
import com.miraleva.ceng301.dto.MembershipCreateRequest;
import com.miraleva.ceng301.dto.MembershipUpdateRequest;
import java.util.List;
import java.util.Optional;

public interface MembershipService {
    List<MembershipResponse> findAll();

    Optional<MembershipResponse> findById(Integer id);

    MembershipResponse create(MembershipCreateRequest request);

    MembershipResponse update(Integer id, MembershipUpdateRequest request);

    void delete(Integer id);
}
