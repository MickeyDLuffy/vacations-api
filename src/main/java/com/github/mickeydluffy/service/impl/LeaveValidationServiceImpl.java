package com.github.mickeydluffy.service.impl;

import com.github.mickeydluffy.exception.LeaveValidationException;
import com.github.mickeydluffy.model.LeaveRequest;
import com.github.mickeydluffy.service.LeaveRequestRepository;
import com.github.mickeydluffy.service.LeaveValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class LeaveValidationServiceImpl implements LeaveValidationService {
    private final LeaveRequestRepository leaveRequestRepository;

    @Override
    public Boolean isValid(LeaveRequest leaveRequest) {
        return isLeaveOverlap().apply(leaveRequest);
    }

    @Override
    public LeaveRequest validate(LeaveRequest request) {
        if (isLeaveOverlap().apply(request)) {
            return request;
        }

        var message = String.format("You have an overlapping leave request" + " applied for leave for the period %s and %s",
            request.getStartDate(),
            request.getEndDate()
        );
        throw new LeaveValidationException(message);
    }

    private Function<LeaveRequest, Boolean> isLeaveOverlap() {
        return leaveRequest -> leaveRequestRepository.findOverlappingLeaveRequests(leaveRequest.getStartDate(),
            leaveRequest.getEndDate(),
            leaveRequest.getEmployee()
        ).isEmpty();
    }

    private Function<LeaveRequest, Boolean> hasDaysForSelectedLeaveType() {
        return leaveRequest -> leaveRequestRepository.findOverlappingLeaveRequests(leaveRequest.getStartDate(),
            leaveRequest.getEndDate(),
            leaveRequest.getEmployee()
        ).isEmpty();
    }

    private boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek().name().startsWith("S");
    }

    //    private Boolean isHoliday
}

