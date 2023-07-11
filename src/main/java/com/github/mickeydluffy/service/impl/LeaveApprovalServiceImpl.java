package com.github.mickeydluffy.service.impl;

import com.github.mickeydluffy.dto.LeaveRequestDto;
import com.github.mickeydluffy.service.LeaveApprovalService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class LeaveApprovalServiceImpl implements LeaveApprovalService {
    @Override
    public void approveLeave(LeaveRequestDto leaveRequestDto) {
        throw new IllegalStateException("Not implemented");
    }

    @Override
    public Page<LeaveRequestDto> fetchLeavesToApprove(Pageable pageable) {
        return null;
    }
}
