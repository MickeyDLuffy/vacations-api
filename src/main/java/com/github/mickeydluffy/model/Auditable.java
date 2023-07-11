package com.github.mickeydluffy.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public abstract class Auditable {
    private String createdBy;
    private LocalDateTime createdTimeStamp;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedTimestamp;
}
