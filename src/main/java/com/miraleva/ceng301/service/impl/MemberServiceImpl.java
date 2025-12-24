package com.miraleva.ceng301.service.impl;

import com.miraleva.ceng301.dto.MemberResponse;
import com.miraleva.ceng301.dto.MemberCreateRequest;
import com.miraleva.ceng301.dto.MemberUpdateRequest;
import com.miraleva.ceng301.dto.mapper.MemberMapper;
import com.miraleva.ceng301.repository.MemberRepository;
import com.miraleva.ceng301.service.MemberService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
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
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public MemberResponse update(Integer id, MemberUpdateRequest request) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void delete(Integer id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
