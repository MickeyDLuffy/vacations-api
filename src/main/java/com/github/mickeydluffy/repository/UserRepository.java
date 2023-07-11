package com.github.mickeydluffy.repository;

import com.github.mickeydluffy.dto.LeaveType;
import com.github.mickeydluffy.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByUsername(String username);

    @Query("{'username': ?0, 'leaveBalance.leaveType': ?1}")
    Optional<Integer> findLeaveDaysByUsernameAndLeaveType(String username, LeaveType leaveType);

    @Query("{'username': ?0, 'leaveBalance.leaveType': ?1}")
    void updateLeaveDaysByUsernameAndLeaveType(String username, LeaveType leaveType, int days);

    @Query(value = "{ 'username' : ?0, 'leaveBalance.leaveType' : ?1 }", fields = "{ '_id' : 0, 'leaveBalance.$' : 1 }")
    Optional<User> findLeaveDaysByUsernamesAndLeaveType(String username, LeaveType leaveType);
}
