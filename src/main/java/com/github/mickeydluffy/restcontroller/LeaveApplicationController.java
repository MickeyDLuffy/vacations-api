package com.github.mickeydluffy.restcontroller;

import com.github.mickeydluffy.dto.LeaveRequestDto;
import com.github.mickeydluffy.dto.LeaveResponseDto;
import com.github.mickeydluffy.repository.UserRepository;
import com.github.mickeydluffy.service.LeaveRequestService;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static com.github.mickeydluffy.util.Constants.API_VERSION;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_VERSION + "/leave-requests")
public class LeaveApplicationController {
    private final LeaveRequestService leaveRequestService;
    private final UserRepository userRepository;

    @PostMapping("/test")
    public LeaveRequestDto applyForLeaved(@RequestBody @Valid LeaveRequestDto leaveRequestDto) {

        return leaveRequestDto;
    }

    @PostMapping
    public CompletableFuture<LeaveResponseDto> applyForLeave(@RequestBody @Valid LeaveRequestDto leaveRequestDto) {
        return leaveRequestService.applyForLeave(leaveRequestDto);
    }

    @GetMapping
    public ResponseEntity<Page<LeaveResponseDto>> fetchLeaveRequests(@Nullable Pageable pageable, @RequestBody String s) {
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
