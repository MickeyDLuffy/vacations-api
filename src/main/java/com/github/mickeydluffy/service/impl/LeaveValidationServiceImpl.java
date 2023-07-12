package com.github.mickeydluffy.service.impl;

import com.github.mickeydluffy.dto.LeaveType;
import com.github.mickeydluffy.exception.LeaveApplicationException;
import com.github.mickeydluffy.exception.LeaveValidationException;
import com.github.mickeydluffy.model.LeaveRequest;
import com.github.mickeydluffy.model.User;
import com.github.mickeydluffy.repository.LeaveRequestRepository;
import com.github.mickeydluffy.repository.UserRepository;
import com.github.mickeydluffy.service.LeaveValidationService;
import com.github.mickeydluffy.util.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class LeaveValidationServiceImpl implements LeaveValidationService {
    private final LeaveRequestRepository leaveRequestRepository;
    private final UserRepository userRepository;

    @Override
    public Boolean isValid(LeaveRequest leaveRequest) {
        return leaveRequestNotOverlapping().apply(leaveRequest);
    }

    @Override
    public LeaveRequest validateLeaveDaysOverlap(LeaveRequest request) {

        if (leaveRequestNotOverlapping().apply(request)) {
            return request;
        }

        var message = String.format(
            "You have an overlapping leave request applied for leave for the period %s and %s",
            request.getStartDate(),
            request.getEndDate()
        );
        throw new LeaveValidationException(message);
    }

    /**
     * This verifies that the start date is atleast a day from day of application, and the end date is in the future to the start date
     * @param leaveRequest: leave request object
     * @return LeaveRequest
     * @throws LeaveValidationException
     */
    public LeaveRequest validateLeaveDates(LeaveRequest leaveRequest) {
        LocalDate startDate = leaveRequest.getStartDate();
        LocalDate endDate = leaveRequest.getEndDate();

        boolean leaveStartDateIsNOTAtleastOneDayAhead = startDate.isBefore(LocalDate.now().plusDays(1));
        if (leaveStartDateIsNOTAtleastOneDayAhead) {
            throw new LeaveValidationException("You leave start date must be at least a day from today");
        }

        boolean isEndDatePriorToStartDate = endDate.isBefore(startDate);
        if (isEndDatePriorToStartDate) {
            throw new LeaveValidationException("Invalid date range: The end date must be after the start date");
        }
        return leaveRequest;
    }

    @Override
    public LeaveRequest validateAvailableLeaveBalance(LeaveRequest leaveRequest) {
        String userName = "user";
        LeaveType leaveType = leaveRequest.getLeaveType();

        User leaveDaysByUsernamesAndLeaveType = userRepository.findLeaveDaysByUsernamesAndLeaveType(userName, leaveType)
            .orElseThrow(() -> new LeaveApplicationException("We could not retrieve users remaining days"));

        long currentNumberOfLeaveDaysForLeaveType = leaveDaysByUsernamesAndLeaveType.getLeaveBalance().get(0).getDays();

        long requestedNumberOfLeaveDays = DateUtils.countWeekdaysBetweenDates(leaveRequest.getStartDate(), leaveRequest.getEndDate());

        if (currentNumberOfLeaveDaysForLeaveType == 0) {
            throw new LeaveValidationException(String.format("You have no %s leave days left! You chilled too early mate", leaveType));
        }

        if (currentNumberOfLeaveDaysForLeaveType < requestedNumberOfLeaveDays) {
            var message = String.format(
                "You have %s %s leave days, and you are requesting for %s days. Quite ambitious of you ei",
                currentNumberOfLeaveDaysForLeaveType,
                leaveType,
                requestedNumberOfLeaveDays
            );
            throw new LeaveValidationException(message);
        }

        return leaveRequest;
    }

    private Function<LeaveRequest, Boolean> leaveRequestNotOverlapping() {
        return leaveRequest -> leaveRequestRepository.findOverlappingLeaveRequests(leaveRequest.getStartDate(),
            leaveRequest.getEndDate(),
            leaveRequest.getEmployee()
        ).isEmpty();
    }
}

