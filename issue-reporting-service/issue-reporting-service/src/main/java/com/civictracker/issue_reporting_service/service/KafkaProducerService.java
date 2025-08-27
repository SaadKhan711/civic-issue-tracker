package com.civictracker.issue_reporting_service.service;

// Add this new import
import com.civictracker.issue_reporting_service.dto.IssueReportedEvent; 

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${app.kafka.topic.issue-reported}")
    private String issueReportedTopic;

    // Change the method to send our new, specific event object
    public void sendIssueReportedEvent(UUID issueId, UUID userId, String category) {
        try {
            // Instead of a generic Map, we now send a strongly-typed object.
            IssueReportedEvent payload = new IssueReportedEvent(
                    issueId.toString(),
                    userId.toString(),
                    category
            );
            
            kafkaTemplate.send(issueReportedTopic, payload);
            log.info("Successfully published 'issue-reported' event for issue ID: {}", issueId);
        } catch (Exception e) {
            log.error("Failed to publish 'issue-reported' event for issue ID: {}. Error: {}", issueId, e.getMessage());
        }
    }
}