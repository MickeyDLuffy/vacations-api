package com.github.mickeydluffy.service;

import com.github.mickeydluffy.dto.LeaveRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LeaveApprovalService {
    void approveLeave(LeaveRequestDto leaveRequestDto);

    Page<LeaveRequestDto> fetchLeavesToApprove(Pageable pageable);
}
