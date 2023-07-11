package com.github.mickeydluffy.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class LeaveApplicationException extends ResponseStatusException {
    public LeaveApplicationException(String reason) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, reason);
    }
}
