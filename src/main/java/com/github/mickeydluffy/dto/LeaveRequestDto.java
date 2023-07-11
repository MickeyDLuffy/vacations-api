package com.github.mickeydluffy.dto;

import com.github.mickeydluffy.model.LeaveRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class LeaveRequestDto {
    @Valid
    private UserDto employee;

    @NotNull(message = "Please provide the start date for leave request")
    @Future(message = "Start date must be in the future")
    private LocalDate startDate;

    @NotNull(message = "Please provide the end date for leave request")
    @Future(message = "End date must be in the future")
    private LocalDate endDate;

    @NotBlank(message = "Provide a reason for applying for leave")
    @Size(max = 100, message = "Reason must be more than 100 characters")
    private String reason;

    @NotNull(message = "Provide the type of leave you are applying for. i.e ANNUAL")
    private LeaveType leaveType;

    public static LeaveRequest toEntity(LeaveRequestDto dto) {
        return LeaveRequest.builder()
            .employee(dto.employee)
            .startDate(dto.startDate)
            .leaveType(dto.leaveType)
            .endDate(dto.endDate)
            .reason(dto.reason)
            .build();
    }
}
