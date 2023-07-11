package com.github.mickeydluffy.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Builder
@Data
public class UserDto {
    private String id;
    private String username;
    private Set<Role> roles;
    @NotNull(message = "Please provide the type of leave you are applying for .i.e Annual, casual")
    private LeaveType leaveType;
    private String manager;
}
