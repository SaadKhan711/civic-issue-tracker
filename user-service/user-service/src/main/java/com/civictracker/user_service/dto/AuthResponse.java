package com.civictracker.user_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

// DTO for the successful login response
@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
}