package com.github.mickeydluffy.service.impl;

import com.github.mickeydluffy.dto.LeaveBalance;
import com.github.mickeydluffy.dto.LeaveType;
import com.github.mickeydluffy.dto.UserDto;
import com.github.mickeydluffy.exception.LeaveApplicationException;
import com.github.mickeydluffy.exception.LeaveValidationException;
import com.github.mickeydluffy.model.LeaveRequest;
import com.github.mickeydluffy.model.User;
import com.github.mickeydluffy.repository.LeaveRequestRepository;
import com.github.mickeydluffy.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class LeaveValidationServiceImplTest {

    @Mock
    private LeaveRequestRepository leaveRequestRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LeaveValidationServiceImpl leaveValidationService;

    private LeaveRequest leaveRequest;

    private User user;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        leaveRequest = new LeaveRequest();
        leaveRequest.setStartDate(LocalDate.of(2023, 8, 1));
        leaveRequest.setEndDate(LocalDate.of(2023, 8, 5));
        leaveRequest.setLeaveType(LeaveType.ANNUAL);

        LeaveBalance leaveBalance = LeaveBalance.builder().leaveType(LeaveType.ANNUAL).days(10).build();

        UserDto.builder().username("mickey").id("").manager("").build();
        user = new User();
        user.setUsername("mickey");
        user.setLeaveBalance(Collections.singletonList(leaveBalance));
    }

    @Test
    void validateLeaveDaysOverlap_shouldReturnLeaveRequest_whenNoOverlappingLeaveRequests() {
        assertEquals(leaveRequest, leaveValidationService.validateLeaveDaysOverlap(leaveRequest));
    }

    @Test
    void validateLeaveDates_shouldReturnLeaveRequest_whenStartDateIsAtLeastOneDayAheadAndEndDateIsAfterStartDate() {
        assertEquals(leaveRequest, leaveValidationService.validateLeaveDates(leaveRequest));
    }

    @Test
    void validateLeaveDates_shouldThrowException_whenStartDateIsNotAtLeastOneDayAhead() {
        leaveRequest.setStartDate(LocalDate.now());

        assertThrows(LeaveValidationException.class, () -> leaveValidationService.validateLeaveDates(leaveRequest));
    }

    @Test
    void validateLeaveDates_shouldThrowException_whenEndDateIsBeforeStartDate() {
        leaveRequest.setEndDate(LocalDate.of(2023, 7, 31));

        assertThrows(LeaveValidationException.class, () -> leaveValidationService.validateLeaveDates(leaveRequest));
    }

    @Test
    void validateAvailableLeaveBalance_shouldThrowException_whenUserNotFound() {
        assertThrows(LeaveApplicationException.class, () -> leaveValidationService.validateAvailableLeaveBalance(leaveRequest));
    }
}
