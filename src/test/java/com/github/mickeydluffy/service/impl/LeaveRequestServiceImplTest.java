package com.github.mickeydluffy.service.impl;

import com.github.mickeydluffy.dto.LeaveRequestDto;
import com.github.mickeydluffy.dto.LeaveResponseDto;
import com.github.mickeydluffy.dto.UserDto;
import com.github.mickeydluffy.event.LeaveCreatedEvent;
import com.github.mickeydluffy.model.LeaveRequest;
import com.github.mickeydluffy.repository.LeaveRequestRepository;
import com.github.mickeydluffy.service.LeaveValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.time.LocalDate;
import java.time.Month;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LeaveRequestServiceImplTest {
    @Mock
    private LeaveRequestRepository leaveRequestRepository;
    @Mock
    private ApplicationEventPublisher eventPublisher;
    @Mock
    private LeaveValidationService leaveValidationService;
    @InjectMocks
    private LeaveRequestServiceImpl leaveRequestService;
    private LeaveRequestDto leaveRequestDto;
    private LeaveResponseDto leaveResponseDto;
    private LeaveRequest leaveRequest;
    @Captor
    private ArgumentCaptor<LeaveCreatedEvent> eventCaptor;

    @BeforeEach
    void setUp() {
        leaveRequestDto = LeaveRequestDto.builder()
            .employee(UserDto.builder().username("mickey").build())
            .reason("My healthy moms 100th birthday")
            .startDate(LocalDate.of(2023, Month.APRIL, 20))
            .endDate(LocalDate.of(2023, Month.MAY, 20))
            .build();

        leaveRequest = LeaveRequestDto.toEntity(leaveRequestDto);
        leaveResponseDto = LeaveResponseDto.fromEntity(leaveRequest);
    }

    @Test
    void saveLeaveRequest() throws ExecutionException, InterruptedException {
        when(leaveValidationService.validateAvailableLeaveBalance(any(LeaveRequest.class))).thenReturn(leaveRequest);
        when(leaveValidationService.validateLeaveDaysOverlap(any(LeaveRequest.class))).thenReturn(leaveRequest);
        when(leaveValidationService.validateLeaveDates(any(LeaveRequest.class))).thenReturn(leaveRequest);
        when(leaveRequestRepository.save(any(LeaveRequest.class))).thenReturn(leaveRequest);

        CompletableFuture<LeaveResponseDto> future = leaveRequestService.applyForLeave(leaveRequestDto);

        LeaveResponseDto result = future.get();
        assertEquals(leaveResponseDto, result);
        verify(leaveValidationService).validateAvailableLeaveBalance(leaveRequest);
        verify(leaveValidationService).validateLeaveDaysOverlap(leaveRequest);
        verify(leaveValidationService).validateLeaveDates(leaveRequest);
        verify(leaveRequestRepository).save(leaveRequest);
    }
}
