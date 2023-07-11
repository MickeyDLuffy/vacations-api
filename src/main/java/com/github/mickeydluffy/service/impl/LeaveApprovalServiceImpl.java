package com.github.mickeydluffy.service.impl;

import com.github.mickeydluffy.dto.LeaveRequestDto;
import com.github.mickeydluffy.dto.LeaveResponseDto;
import com.github.mickeydluffy.dto.LeaveStatus;
import com.github.mickeydluffy.model.User;
import com.github.mickeydluffy.repository.LeaveRequestRepository;
import com.github.mickeydluffy.repository.UserRepository;
import com.github.mickeydluffy.service.LeaveApprovalService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LeaveApprovalServiceImpl implements LeaveApprovalService {
    private final LeaveRequestRepository leaveRequestRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public void approveLeave(LeaveRequestDto leaveRequestDto) {
        var leave = leaveRequestRepository.findById(leaveRequestDto.getId()).orElseThrow(RuntimeException::new);
        leave.setStatus(LeaveStatus.APPROVED);
        leaveRequestRepository.save(leave);
    }

    @Override
    public Page<LeaveResponseDto> fetchLeavesToApprove(Pageable pageable) {
        User user = userRepository.findByUsername("jhey").get();
        return leaveRequestRepository.findLeaveRequestsByManagerId(user.getId(), pageable).map(LeaveResponseDto::fromEntity);
    }
}
