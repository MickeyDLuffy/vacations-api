package com.github.mickeydluffy.model;

import com.github.mickeydluffy.dto.LeaveBalance;
import com.github.mickeydluffy.dto.Role;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document(collection = "users")
@Data
public class User extends Auditable {
    @Id
    private String id;
    private String username;
    private String password;
    private Set<Role> roles;
    private LeaveBalance leaveBalance;
}
