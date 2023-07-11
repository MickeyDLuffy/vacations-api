package com.github.mickeydluffy.service;

import com.github.mickeydluffy.model.LeaveRequest;

public interface LeaveValidationService {

    Boolean isValid(LeaveRequest leaveRequest);

    LeaveRequest validate(LeaveRequest request);
}
