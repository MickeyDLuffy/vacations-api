package com.github.mickeydluffy.service.impl;

import com.github.mickeydluffy.dto.LeaveRequestDto;
import com.github.mickeydluffy.dto.LeaveResponseDto;
import com.github.mickeydluffy.service.LeaveRequestRepository;
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
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LeaveRequestServiceImpl implements LeaveRequestService {
    private final LeaveRequestRepository leaveRequestRepository;
    private final LeaveValidationService leaveValidationService;

    @Override
    public LeaveResponseDto applyForLeave(LeaveRequestDto dto) {
        try {
            return CompletableFuture.supplyAsync(() -> LeaveRequestDto.toEntity(dto))
                .thenApply(leaveValidationService::validate)
                .thenApply(leaveRequestRepository::save)
                .thenApply(LeaveResponseDto::fromEntity)
                //                .exceptionally(error -> {throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, error.getMessage());})
                .get();
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
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
