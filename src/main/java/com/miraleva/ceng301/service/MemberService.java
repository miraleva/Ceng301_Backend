package com.miraleva.ceng301.service;

import com.miraleva.ceng301.dto.MemberDto;
import java.util.List;
import java.util.Optional;

public interface MemberService {
    List<MemberDto> findAll();

    Optional<MemberDto> findById(Long id);

    MemberDto create(MemberDto memberDto);

    MemberDto update(Long id, MemberDto memberDto);

    void delete(Long id);
}
