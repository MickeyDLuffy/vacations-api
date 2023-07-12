package com.github.mickeydluffy.event.listener;

import com.github.mickeydluffy.dto.LeaveResponseDto;
import com.github.mickeydluffy.dto.UserDto;
import com.github.mickeydluffy.event.Event;
import com.github.mickeydluffy.event.scheduler.EmailScheduler;
import com.github.mickeydluffy.service.EmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LeaveEventListeners {
    private final EmailSender emailSender;
    private final EmailScheduler scheduler;

    @EventListener
    public void onLeaveCreatedEvent(Event event) {
        if (event.getType() == Event.LEAVE_CREATED) {
            sendEmail(event.getPayload());
        }
    }

    private void sendEmail(LeaveResponseDto leaveResponseDto) {
        UserDto employee = leaveResponseDto.getEmployee();
        UserDto manager = employee.getManager();

        var message = String.format("Your %s leave application from %s to %s has been captured, and waiting for approval",
            leaveResponseDto.getLeaveType(),
            leaveResponseDto.getStartDate(),
            leaveResponseDto.getEndDate()
        );
        emailSender.sendEmail(employee.getEmail(), "Leave application started", message);

        var managerMessage =
            String.format("You have a leave request from %s to approve. Kindly login to vacay to approve", employee.getUsername());
        emailSender.sendEmail(manager.getEmail(), "Approve leave request", managerMessage);
        scheduler.startScheduledTask(leaveResponseDto);
    }
}
