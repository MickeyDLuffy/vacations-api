package com.github.mickeydluffy.event.scheduler;

import com.github.mickeydluffy.dto.LeaveResponseDto;
import com.github.mickeydluffy.dto.UserDto;
import com.github.mickeydluffy.service.EmailSender;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.concurrent.ScheduledFuture;

@Component
@RequiredArgsConstructor
public class EmailScheduler {
    private final EmailSender emailSender;
    private final TaskScheduler taskScheduler;
    private ScheduledFuture<?> scheduledEmailSenderTask;
    private ScheduledFuture<?> stopTask;

    public void startScheduledTask(LeaveResponseDto leaveResponseDto) {
        LocalDate currentDate = LocalDate.now();
        var endDate = leaveResponseDto.getEndDate();
        LocalDate executionDate = endDate.minusDays(3);

        if (currentDate.isBefore(executionDate)) {
            String cronExpression = calculateCronExpression(executionDate);
            CronTrigger cronTrigger = new CronTrigger(cronExpression);

            scheduledEmailSenderTask = taskScheduler.schedule(() -> {
                UserDto employee = leaveResponseDto.getEmployee();
                String recipient = employee.getManager().getEmail();
                String subject = "Leave Approval Email";
                String body = String.format("Please approve the leave request from %s which ends on %s",
                    employee.getUsername(),
                    leaveResponseDto.getEndDate()
                );

                emailSender.sendEmail(recipient, subject, body);
            }, cronTrigger);

            stopTask = taskScheduler.schedule(this::stopScheduledTask, new CronTrigger(calculateCronExpression(endDate)));
        }
    }

    public void stopScheduledTask() {
        if (scheduledEmailSenderTask != null) {
            scheduledEmailSenderTask.cancel(true);
        }

        if (stopTask != null) {
            stopTask.cancel(true);
        }
    }

    private String calculateCronExpression(LocalDate executionDate) {
        int dayOfMonth = executionDate.getDayOfMonth();
        return String.format("0 0 9 %d * ?", dayOfMonth);
    }
}
