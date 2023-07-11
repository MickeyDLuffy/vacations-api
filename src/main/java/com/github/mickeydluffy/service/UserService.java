package com.github.mickeydluffy.service;

import com.github.mickeydluffy.dto.LeaveType;

public interface UserService {
    void updateUserLeaveDays(LeaveType leaveType, int newLeaveDays);
}
