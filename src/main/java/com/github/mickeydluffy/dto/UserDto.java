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
    private String email;
    private Set<Role> roles;
    private UserDto manager;

    public static User toEntity(UserDto user) {
        User entity = new User();
        entity.setId(user.getId());
        entity.setRoles(user.getRoles());
        entity.setUsername(user.getUsername());
        entity.setManager(user.getManager());
        entity.setEmail(user.getEmail());

        return entity;
    }
}
