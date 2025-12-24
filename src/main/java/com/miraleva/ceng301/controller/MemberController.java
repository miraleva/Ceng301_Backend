package com.miraleva.ceng301.controller;

import com.miraleva.ceng301.dto.ApiResponse;
import com.miraleva.ceng301.dto.MemberDto;
import com.miraleva.ceng301.service.MemberService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public ApiResponse<List<MemberDto>> getAll() {
        return ApiResponse.success("Fetched successfully", memberService.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<MemberDto> getById(@PathVariable Long id) {
        return ApiResponse.success("Fetched successfully", memberService.findById(id).orElse(null));
    }

    @PostMapping
    public ApiResponse<MemberDto> create(@RequestBody MemberDto memberDto) {
        return ApiResponse.success("Created successfully", memberService.create(memberDto));
    }

    @PutMapping("/{id}")
    public ApiResponse<MemberDto> update(@PathVariable Long id, @RequestBody MemberDto memberDto) {
        return ApiResponse.success("Updated successfully", memberService.update(id, memberDto));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        memberService.delete(id);
        return ApiResponse.success("Deleted successfully", null);
    }
}
