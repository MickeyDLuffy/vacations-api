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
    private LeaveType leaveType;
    private LocalDate leaveModifiedTime;
    private LocalDate leaveCreationTime;
    private String leaveCreatedBy;
    private String leaveModifiedBy;

    public static LeaveResponseDto fromEntity(LeaveRequest entity) {
        return LeaveResponseDto.builder()
            .id(entity.getId())
            .employee(entity.getEmployee())
            .startDate(entity.getStartDate())
            .endDate(entity.getEndDate())
            .reason(entity.getReason())
            .status(entity.getStatus())
            .leaveType(entity.getLeaveType())
            .employee(entity.getEmployee())
            .leaveCreatedBy(entity.getCreatedBy())
            .leaveCreationTime(entity.getCreatedTimeStamp())
            .leaveCreatedBy(entity.getCreatedBy())
            .leaveModifiedBy(entity.getLastModifiedBy())
            .build();
    }
}
