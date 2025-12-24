package com.miraleva.ceng301.service.impl;

import com.miraleva.ceng301.dto.MemberDto;
import com.miraleva.ceng301.repository.MemberRepository;
import com.miraleva.ceng301.service.MemberService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    public MemberServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public List<MemberDto> findAll() {
        return Collections.emptyList();
    }

    @Override
    public Optional<MemberDto> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public MemberDto create(MemberDto memberDto) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public MemberDto update(Long id, MemberDto memberDto) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void delete(Long id) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}
