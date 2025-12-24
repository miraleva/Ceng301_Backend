package com.miraleva.ceng301.service;

import com.miraleva.ceng301.dto.MemberResponse;
import com.miraleva.ceng301.dto.MemberCreateRequest;
import com.miraleva.ceng301.dto.MemberUpdateRequest;
import java.util.List;
import java.util.Optional;

public interface MemberService {
    List<MemberResponse> findAll();

    Optional<MemberResponse> findById(Integer id);

    MemberResponse create(MemberCreateRequest request);

    MemberResponse update(Integer id, MemberUpdateRequest request);

    void delete(Integer id);
}
