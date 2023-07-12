package com.github.mickeydluffy.model;

import com.github.mickeydluffy.dto.LeaveBalance;
import com.github.mickeydluffy.dto.Role;
import com.github.mickeydluffy.dto.UserDto;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@Document(collection = "users")
@Data
public class User extends Auditable {
    @Id
    private String id;
    private String username;
    private String email;
    private String password;
    private Set<Role> roles;
    private List<LeaveBalance> leaveBalance;
    private UserDto manager;

    public static UserDto fromEntity(User user) {
        return UserDto.builder()
            .id(user.getId())
            .roles(user.getRoles())
            .username(user.getUsername())
            .email(user.getEmail())
            .manager(user.getManager())
            .build();
    }
}
