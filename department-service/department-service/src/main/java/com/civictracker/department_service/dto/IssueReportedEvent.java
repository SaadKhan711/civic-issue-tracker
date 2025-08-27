package com.civictracker.department_service.dto;

import lombok.Data;

@Data
public class IssueReportedEvent {
    private String issueId;
    private String userId;
    private String category;
}