package com.github.mickeydluffy.service.impl;

import com.github.mickeydluffy.dto.LeaveRequestDto;
import com.github.mickeydluffy.dto.LeaveResponseDto;
import com.github.mickeydluffy.dto.UserDto;
import com.github.mickeydluffy.model.LeaveRequest;
import com.github.mickeydluffy.service.LeaveRequestRepository;
import com.github.mickeydluffy.service.LeaveValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LeaveRequestServiceImplTest {
    @Mock
    private LeaveRequestRepository leaveRequestRepository;
    @Mock
    private LeaveValidationService leaveValidationService;
    @InjectMocks
    private LeaveRequestServiceImpl leaveRequestService;
    private LeaveRequestDto leaveRequestDto;
    private LeaveRequest leaveRequest;

    @BeforeEach
    void setUp() {
        leaveRequestDto =
            LeaveRequestDto.builder().employee(UserDto.builder().username("mickey").build()).reason("My healthy moms 100th birthday")
            .startDate(LocalDate.of(2023, Month.APRIL, 20))
            .endDate(LocalDate.of(2023, Month.MAY, 20))
            .build();

        leaveRequest = LeaveRequestDto.toEntity(leaveRequestDto);
    }

    @Test
    void saveLeaveRequest() {
        when(leaveRequestRepository.save(leaveRequest)).thenReturn(leaveRequest);
        when(leaveValidationService.validate(leaveRequest)).thenReturn(leaveRequest);
        LeaveResponseDto result = leaveRequestService.applyForLeave(leaveRequestDto);
        assertEquals(leaveRequestDto.getStartDate(), result.getStartDate());
        assertEquals(leaveRequestDto.getEmployee(), result.getUser());
        verify(leaveRequestRepository, times(1)).save(leaveRequest);
    }
}
