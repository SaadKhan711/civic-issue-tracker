package com.civictracker.issue_reporting_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueReportedEvent {
    private String issueId;
    private String userId;
    private String category;
}