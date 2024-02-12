package com.github.mickeydluffy.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LeaveBalance {
    @NotNull(message = "You must have leave days available to be able to apply for leave")
    @Min(value = 0, message = "You cant possible have negative leave days")
    private int days;
    private LeaveType leaveType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LeaveBalance that)) return false;

        if (days != that.days) return false;
        return leaveType == that.leaveType;
    }

    @Override
    public int hashCode() {
        int result = days;
        result = 31 * result + (leaveType != null ? leaveType.hashCode() : 0);
        return result;
    }
}
