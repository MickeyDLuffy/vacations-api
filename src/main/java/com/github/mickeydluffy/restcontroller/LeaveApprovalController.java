package com.github.mickeydluffy.restcontroller;

import com.github.mickeydluffy.dto.LeaveRequestDto;
import com.github.mickeydluffy.service.LeaveApprovalService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.github.mickeydluffy.util.Constants.API_VERSION;

@RestController
@RequiredArgsConstructor
@RequestMapping(API_VERSION + "/leave-approvals")
public class LeaveApprovalController {
    private final LeaveApprovalService leaveApprovalService;

    @PostMapping
    public void approveLeave(@RequestBody LeaveRequestDto leaveRequestDto) {
        leaveApprovalService.approveLeave(leaveRequestDto);
    }

    @GetMapping
    public ResponseEntity<Page<LeaveRequestDto>> fetchLeavesToApprove(Pageable pageable) {
        return ResponseEntity.ok(leaveApprovalService.fetchLeavesToApprove(pageable));
    }
}
