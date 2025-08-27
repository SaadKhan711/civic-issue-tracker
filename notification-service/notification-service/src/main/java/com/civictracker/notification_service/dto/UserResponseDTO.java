package com.civictracker.notification_service.dto;

import lombok.Data;

// This DTO represents the response we get from the user-service.
@Data
public class UserResponseDTO {
    private String id;
    private String name;
    private String email;
}