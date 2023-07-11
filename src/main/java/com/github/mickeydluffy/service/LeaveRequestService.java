package com.github.mickeydluffy.service;

import com.github.mickeydluffy.dto.LeaveRequestDto;
import com.github.mickeydluffy.dto.LeaveResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface LeaveRequestService {
    LeaveResponseDto applyForLeave(LeaveRequestDto leaveRequest);

    Set<LeaveResponseDto> fetchLeaveRequests();

    Page<LeaveResponseDto> fetchLeaveRequests(Pageable pageable);

    LeaveResponseDto fetchLeaveRequestById(String id);

    Integer fetchRemainingLeaveDays();
}
