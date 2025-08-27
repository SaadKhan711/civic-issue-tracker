package com.civictracker.issue_reporting_service.dto;

import com.civictracker.issue_reporting_service.model.Category;
import com.civictracker.issue_reporting_service.model.Status;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class IssueResponseDTO {
    private UUID id;
    private UUID userId;
    private String title;
    private String description;
    private Double latitude;
    private Double longitude;
    private String imageUrl;
    private Category category;
    private Status status;
    private LocalDateTime createdAt;
}