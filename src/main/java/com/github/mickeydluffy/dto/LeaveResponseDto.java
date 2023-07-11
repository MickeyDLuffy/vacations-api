package com.github.mickeydluffy.dto;

import com.github.mickeydluffy.model.LeaveRequest;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class LeaveResponseDto {
    private String id;
    private UserDto employee;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
    private LeaveStatus status;

    public static LeaveResponseDto fromEntity(LeaveRequest entity) {
        return LeaveResponseDto.builder().id(entity.getId()).employee(entity.getEmployee())
            .startDate(entity.getStartDate())
            .endDate(entity.getEndDate())
            .reason(entity.getReason())
            .status(entity.getStatus())
            .build();
    }
}
