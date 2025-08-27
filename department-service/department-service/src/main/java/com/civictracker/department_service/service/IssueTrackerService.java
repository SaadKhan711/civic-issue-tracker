package com.civictracker.department_service.service;

import com.civictracker.department_service.dto.IssueReportedEvent;
import com.civictracker.department_service.model.Category;
import com.civictracker.department_service.model.Status;
import com.civictracker.department_service.model.TrackedIssue;
import com.civictracker.department_service.repository.DepartmentRepository;
import com.civictracker.department_service.repository.TrackedIssueRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class IssueTrackerService {

    private final TrackedIssueRepository trackedIssueRepository;
    private final DepartmentRepository departmentRepository;

    @KafkaListener(topics = "${app.kafka.topic.issue-reported}", groupId = "department-group")
    public void handleIssueReportedEvent(IssueReportedEvent event) {
        log.info("Received issue reported event for tracking: {}", event);

        TrackedIssue trackedIssue = new TrackedIssue();
        trackedIssue.setIssueId(UUID.fromString(event.getIssueId()));
        trackedIssue.setUserId(UUID.fromString(event.getUserId()));
        trackedIssue.setStatus(Status.RECEIVED);

        // Simple logic: Automatically assign a department based on the issue category.
        try {
            Category category = Category.valueOf(event.getCategory());
            departmentRepository.findByCategory(category).ifPresent(department -> {
                trackedIssue.setDepartmentId(department.getId());
                trackedIssue.setStatus(Status.ASSIGNED);
                log.info("Automatically assigned issue {} to department {}", event.getIssueId(), department.getName());
            });
        } catch (IllegalArgumentException e) {
            log.warn("Received issue with unknown category: {}. Issue will not be auto-assigned.", event.getCategory());
        }

        trackedIssueRepository.save(trackedIssue);
        log.info("Successfully created tracking record for issue {}", event.getIssueId());
    }
}