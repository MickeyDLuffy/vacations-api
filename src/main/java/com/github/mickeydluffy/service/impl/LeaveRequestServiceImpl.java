package com.github.mickeydluffy.service.impl;

import com.github.mickeydluffy.dto.LeaveRequestDto;
import com.github.mickeydluffy.dto.LeaveResponseDto;
import com.github.mickeydluffy.repository.LeaveRequestRepository;
import com.github.mickeydluffy.service.LeaveRequestService;
import com.github.mickeydluffy.service.LeaveValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LeaveRequestServiceImpl implements LeaveRequestService {
    private final LeaveRequestRepository leaveRequestRepository;
    private final LeaveValidationService leaveValidationService;

    @Override
    public CompletableFuture<LeaveResponseDto> applyForLeave(LeaveRequestDto dto) {

        return CompletableFuture.supplyAsync(() -> LeaveRequestDto.toEntity(dto))
            .thenApply(leaveValidationService::validateAvailableLeaveBalance)
            .thenApply(leaveValidationService::validateLeaveDaysOverlap)
            .thenApply(leaveValidationService::validateLeaveDates)
            .thenApply(leaveRequestRepository::save)
            .thenApply(LeaveResponseDto::fromEntity);
    }

    @Override
    public Set<LeaveResponseDto> fetchLeaveRequests() {
        return leaveRequestRepository.findAll().stream().map(LeaveResponseDto::fromEntity).collect(Collectors.toSet());
    }

    @Override
    public Page<LeaveResponseDto> fetchLeaveRequests(Pageable pageable) {
        return leaveRequestRepository.findAll(pageable).map(LeaveResponseDto::fromEntity);
    }

    @Override
    public LeaveResponseDto fetchLeaveRequestById(String id) {
        return leaveRequestRepository.findById(id)
            .map(LeaveResponseDto::fromEntity)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Leave with id %s does not exist", id)));
    }

    @Override
    public Integer fetchRemainingLeaveDays() {
        return null;
    }
}
