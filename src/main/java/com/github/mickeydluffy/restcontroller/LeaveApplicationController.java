package com.github.mickeydluffy.restcontroller;

import com.github.mickeydluffy.dto.LeaveRequestDto;
import com.github.mickeydluffy.dto.LeaveResponseDto;
import com.github.mickeydluffy.service.LeaveRequestService;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

import static com.github.mickeydluffy.util.Constants.API_VERSION;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_VERSION + "/leave-requests")
public class LeaveApplicationController {
    private final LeaveRequestService leaveRequestService;

    @PostMapping
    public ResponseEntity<LeaveResponseDto> applyForLeave(@RequestBody @Valid LeaveRequestDto leaveRequestDto) {
        LeaveResponseDto leaveResponse = leaveRequestService.applyForLeave(leaveRequestDto);
        URI uri = URI.create(String.format("%s/leave-requests/%s", API_VERSION, leaveResponse.getId()));
        return ResponseEntity.created(uri).body(leaveResponse);
    }

    @GetMapping
    public ResponseEntity<Page<LeaveResponseDto>> fetchLeaveRequests(@Nullable Pageable pageable) {
        return ResponseEntity.ok(leaveRequestService.fetchLeaveRequests(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeaveResponseDto> fetchLeaveRequestById(@PathVariable String id) {
        return ResponseEntity.ok(leaveRequestService.fetchLeaveRequestById(id));
    }

    @GetMapping("/leave-balance")
    public ResponseEntity<Map<String, Integer>> fetchRemainingLeaveDays() {
        return ResponseEntity.ok(Map.of("leaveBalance", leaveRequestService.fetchRemainingLeaveDays()));
    }
}
