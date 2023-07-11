package com.github.mickeydluffy.dto;

import com.github.mickeydluffy.model.LeaveRequest;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class LeaveRequestDto {
    private UserDto employee;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;

    public static LeaveRequest toEntity(LeaveRequestDto dto) {
        return LeaveRequest.builder().employee(dto.employee).startDate(dto.startDate).endDate(dto.endDate).reason(dto.reason).build();
    }
}
