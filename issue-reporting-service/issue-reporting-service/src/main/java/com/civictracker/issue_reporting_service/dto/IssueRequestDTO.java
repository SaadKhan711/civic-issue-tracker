package com.civictracker.issue_reporting_service.dto;

import com.civictracker.issue_reporting_service.model.Category;
import lombok.Data;

@Data
public class IssueRequestDTO {
    private String title;
    private String description;
    private Double latitude;
    private Double longitude;
    private String imageUrl; // Optional
    private Category category;
}