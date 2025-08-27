package com.civictracker.notification_service.dto;

import lombok.Data;

// This DTO represents the message we receive from Kafka.
// The field names MUST match the JSON keys exactly.
@Data
public class IssueReportedEvent {
    private String issueId;
    private String userId;
    private String category;
}