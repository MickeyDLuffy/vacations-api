package com.github.mickeydluffy.model;

import com.github.mickeydluffy.dto.LeaveStatus;
import com.github.mickeydluffy.dto.LeaveType;
import com.github.mickeydluffy.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "leave_requests")
public class LeaveRequest extends Auditable {
    @Id
    private String id;
    private UserDto employee;
    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;
    @Builder.Default
    private LeaveStatus status = LeaveStatus.PENDING;
    private LeaveType leaveType;
}

