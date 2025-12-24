package com.miraleva.ceng301.controller;

import com.miraleva.ceng301.dto.ApiResponse;
import com.miraleva.ceng301.dto.MembershipDto;
import com.miraleva.ceng301.service.MembershipService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/memberships")
public class MembershipController {

    private final MembershipService membershipService;

    public MembershipController(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    @GetMapping
    public ApiResponse<List<MembershipDto>> getAll() {
        return ApiResponse.success("Fetched successfully", membershipService.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<MembershipDto> getById(@PathVariable Long id) {
        return ApiResponse.success("Fetched successfully", membershipService.findById(id).orElse(null));
    }

    @PostMapping
    public ApiResponse<MembershipDto> create(@RequestBody MembershipDto membershipDto) {
        return ApiResponse.success("Created successfully", membershipService.create(membershipDto));
    }

    @PutMapping("/{id}")
    public ApiResponse<MembershipDto> update(@PathVariable Long id, @RequestBody MembershipDto membershipDto) {
        return ApiResponse.success("Updated successfully", membershipService.update(id, membershipDto));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        membershipService.delete(id);
        return ApiResponse.success("Deleted successfully", null);
    }
}
