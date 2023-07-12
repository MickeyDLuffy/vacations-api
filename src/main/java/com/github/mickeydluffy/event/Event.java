package com.github.mickeydluffy.event;

import com.github.mickeydluffy.dto.LeaveResponseDto;

public interface Event {
    String LEAVE_CREATED = "LeaveCreated";
    String LEAVE_APPROVED = "LeaveApproved";

    String getType();

    LeaveResponseDto getPayload();
}
