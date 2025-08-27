package com.civictracker.notification_service.service;

import com.civictracker.notification_service.dto.UserResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
@Slf4j
public class UserServiceClient {

    private final RestTemplate restTemplate;
    private final String userServiceUrl;

    // We inject the RestTemplate and the URL from application.properties
    public UserServiceClient(RestTemplate restTemplate, @Value("${app.user-service.url}") String userServiceUrl) {
        this.restTemplate = restTemplate;
        this.userServiceUrl = userServiceUrl;
    }

    public Optional<UserResponseDTO> getUserById(String userId) {
        String url = userServiceUrl + "/" + userId;
        try {
            log.info("Attempting to fetch user details from: {}", url);
            UserResponseDTO user = restTemplate.getForObject(url, UserResponseDTO.class);
            log.info("Successfully fetched user details for user ID: {}", userId);
            return Optional.ofNullable(user);
        } catch (Exception e) {
            log.error("Failed to fetch user details for user ID {}. Error: {}", userId, e.getMessage());
            return Optional.empty();
        }
    }
}
