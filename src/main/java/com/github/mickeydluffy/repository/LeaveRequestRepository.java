package com.github.mickeydluffy.repository;

import com.github.mickeydluffy.dto.UserDto;
import com.github.mickeydluffy.model.LeaveRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LeaveRequestRepository extends MongoRepository<LeaveRequest, String> {

    @Query("{ employee: ?2, $or: [ { startDate: { $lt: ?1 }, endDate: { $gt: ?0 } }, { startDate: { $lte: ?1 }, endDate: { $gte: ?0 } } ] }")
    List<LeaveRequest> findOverlappingLeaveRequests(LocalDate startDate, LocalDate endDate, UserDto employeeId);

    @Query(value = "{ 'employee.manager': ?0 }", count = true)
    Page<LeaveRequest> findLeaveRequestsByManagerId(String managerId, Pageable pageable);
}
