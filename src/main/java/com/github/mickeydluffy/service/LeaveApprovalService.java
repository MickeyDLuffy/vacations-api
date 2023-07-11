package com.github.mickeydluffy.service;

import com.github.mickeydluffy.dto.LeaveRequestDto;
import com.github.mickeydluffy.dto.LeaveResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LeaveApprovalService {
    void approveLeave(LeaveRequestDto leaveRequestDto);

    Page<LeaveResponseDto> fetchLeavesToApprove(Pageable pageable);
}
