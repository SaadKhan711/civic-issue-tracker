package com.civictracker.notification_service.service;

import com.civictracker.notification_service.dto.IssueReportedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final UserServiceClient userServiceClient;
    private final EmailService emailService;

    // This is the magic part. This method will automatically be called
    // whenever a message appears on the "issue-reported" topic.
    @KafkaListener(topics = "${app.kafka.topic.issue-reported}", groupId = "notification-group")
    public void handleIssueReportedEvent(IssueReportedEvent event) {
        log.info("Received issue reported event: {}", event);

        // 1. Get the user's details from the User Service
        userServiceClient.getUserById(event.getUserId()).ifPresent(user -> {
            // 2. If the user is found, send the email
            emailService.sendConfirmationEmail(user.getEmail(), user.getName(), event.getIssueId());
        });
    }
}