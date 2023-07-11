package com.github.mickeydluffy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class LeaveValidationException extends ResponseStatusException {
    public LeaveValidationException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }
}
