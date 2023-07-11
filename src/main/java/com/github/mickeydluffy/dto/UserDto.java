package com.github.mickeydluffy.dto;

import com.github.mickeydluffy.model.User;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Builder
@Data
public class UserDto {
    private String id;
    private String username;
    private Set<Role> roles;
    private LeaveBalance leaveBalance;
    private User manager;
}
