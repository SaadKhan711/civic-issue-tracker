package com.civictracker.user_service.dto;

import lombok.Data;

// DTO for the login request body
@Data
public class AuthRequest {
    private String email;
    private String password;
}