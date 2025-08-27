package com.civictracker.issue_reporting_service.controller;

import com.civictracker.issue_reporting_service.dto.IssueRequestDTO;
import com.civictracker.issue_reporting_service.dto.IssueResponseDTO;
import com.civictracker.issue_reporting_service.service.IssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication; 
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/issues")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class IssueController {

    private final IssueService issueService;

    @PostMapping
    public ResponseEntity<IssueResponseDTO> createIssue(
            @RequestBody IssueRequestDTO issueRequest,
            Authentication authentication // <-- GET AUTHENTICATION FROM SPRING SECURITY
    ) {
        // We get the user's email (username) from the authenticated principal
        String userEmail = authentication.getName();

        // NOTE: The JWT only contains the user's email. It does NOT contain their UUID.
        // For our MVP, we will pass the email to the service layer. In a more advanced
        // system, you might make another call to the user-service to get the full user object.
        // For now, we will use a placeholder UUID, as the email is the important part for notifications.
        // This is a design trade-off we are making for simplicity.
        UUID userId = UUID.nameUUIDFromBytes(userEmail.getBytes()); // Create a consistent UUID from the email

        IssueResponseDTO createdIssue = issueService.createIssue(issueRequest, userId);
        return new ResponseEntity<>(createdIssue, HttpStatus.CREATED);
    }
}