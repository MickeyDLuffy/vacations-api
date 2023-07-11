package com.github.mickeydluffy.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Builder
@Data
public class UserDto {
    private String id;
    private String username;
    private Set<Role> roles;
    private String manager;
}
