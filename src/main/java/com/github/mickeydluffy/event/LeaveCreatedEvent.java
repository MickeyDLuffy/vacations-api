package com.github.mickeydluffy.event;

import com.github.mickeydluffy.dto.LeaveResponseDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class LeaveCreatedEvent implements Event {
    private final String id;
    private final LeaveResponseDto payload;

    public static LeaveCreatedEvent of(LeaveResponseDto leaveRequest) {
        return new LeaveCreatedEvent(leaveRequest.getId(), leaveRequest);
    }

    @Override
    public String getType() {
        return Event.LEAVE_CREATED;
    }

    @Override
    public LeaveResponseDto getPayload() {
        return this.payload;
    }
}
