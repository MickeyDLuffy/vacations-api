package com.github.mickeydluffy.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LeaveBalance {
    private int days;
    private LeaveType leaveType;
}
