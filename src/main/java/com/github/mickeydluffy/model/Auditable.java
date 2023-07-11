package com.github.mickeydluffy.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDate;

@Data
public class Auditable {
    @CreatedBy
    private String createdBy;
    @CreatedDate
    private LocalDate createdTimeStamp;
    @LastModifiedBy
    private String lastModifiedBy;
    @LastModifiedDate
    private LocalDate lastModifiedTimestamp;
}
