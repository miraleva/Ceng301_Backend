package com.miraleva.ceng301.controller;

import com.miraleva.ceng301.dto.ApiResponse;
import com.miraleva.ceng301.dto.MembershipResponse;
import com.miraleva.ceng301.dto.MembershipCreateRequest;
import com.miraleva.ceng301.dto.MembershipUpdateRequest;
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
    public ApiResponse<List<MembershipResponse>> getAll() {
        return ApiResponse.success("Fetched successfully", membershipService.findAll());
    }

    @GetMapping("/{id}")
    public ApiResponse<MembershipResponse> getById(@PathVariable Integer id) {
        return ApiResponse.success("Fetched successfully", membershipService.findById(id).orElse(null));
    }

    @PostMapping
    public ApiResponse<MembershipResponse> create(@RequestBody MembershipCreateRequest request) {
        return ApiResponse.success("Created successfully", membershipService.create(request));
    }

    @PutMapping("/{id}")
    public ApiResponse<MembershipResponse> update(@PathVariable Integer id,
            @RequestBody MembershipUpdateRequest request) {
        return ApiResponse.success("Updated successfully", membershipService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Integer id) {
        membershipService.delete(id);
        return ApiResponse.success("Deleted successfully", null);
    }
}
