package com.miraleva.ceng301.controller;

import com.miraleva.ceng301.dto.ApiResponse;
import com.miraleva.ceng301.dto.MemberResponse;
import com.miraleva.ceng301.dto.MemberCreateRequest;
import com.miraleva.ceng301.dto.MemberUpdateRequest;
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
    public ApiResponse<List<MemberResponse>> getAll() {
        return ApiResponse.success("Fetched successfully", memberService.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<MemberResponse> getById(@PathVariable Integer id) {
        return ApiResponse.success("Fetched successfully", memberService.findById(id).orElse(null));
    }

    @PostMapping
    public ApiResponse<MemberResponse> create(@RequestBody MemberCreateRequest request) {
        return ApiResponse.success("Created successfully", memberService.create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<MemberResponse> update(@PathVariable Integer id, @RequestBody MemberUpdateRequest request) {
        return ApiResponse.success("Updated successfully", memberService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Integer id) {
        memberService.delete(id);
        return ApiResponse.success("Deleted successfully", null);
    }
}
