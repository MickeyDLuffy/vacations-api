package com.github.mickeydluffy.service.impl;

import com.github.mickeydluffy.dto.LeaveType;
import com.github.mickeydluffy.repository.UserRepository;
import com.github.mickeydluffy.service.UserService;
import com.github.mickeydluffy.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public void updateUserLeaveDays(LeaveType leaveType, int newLeaveDays) {
        userRepository.updateLeaveDaysByUsernameAndLeaveType(SecurityUtil.getUserName(), leaveType, newLeaveDays);
    }
}
