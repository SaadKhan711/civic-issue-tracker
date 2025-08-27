package com.civictracker.issue_reporting_service.service;

import com.civictracker.issue_reporting_service.dto.IssueRequestDTO;
import com.civictracker.issue_reporting_service.dto.IssueResponseDTO;
import com.civictracker.issue_reporting_service.model.Issue;
import com.civictracker.issue_reporting_service.model.Status;
import com.civictracker.issue_reporting_service.repository.IssueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IssueService {

    private final IssueRepository issueRepository;
    private final KafkaProducerService kafkaProducerService;

    @Transactional
    public IssueResponseDTO createIssue(IssueRequestDTO requestDTO, UUID userId) {
        Issue issue = new Issue();
        issue.setUserId(userId);
        issue.setTitle(requestDTO.getTitle());
        issue.setDescription(requestDTO.getDescription());
        issue.setLatitude(requestDTO.getLatitude());
        issue.setLongitude(requestDTO.getLongitude());
        issue.setImageUrl(requestDTO.getImageUrl());
        issue.setCategory(requestDTO.getCategory());
        issue.setStatus(Status.RECEIVED);

        Issue savedIssue = issueRepository.save(issue);

        kafkaProducerService.sendIssueReportedEvent(
            savedIssue.getId(),
            savedIssue.getUserId(),
            savedIssue.getCategory().name()
        );

        return mapToResponseDTO(savedIssue);
    }

    private IssueResponseDTO mapToResponseDTO(Issue issue) {
        IssueResponseDTO dto = new IssueResponseDTO();
        dto.setId(issue.getId());
        dto.setUserId(issue.getUserId());
        dto.setTitle(issue.getTitle());
        dto.setDescription(issue.getDescription());
        dto.setLatitude(issue.getLatitude());
        dto.setLongitude(issue.getLongitude());
        dto.setImageUrl(issue.getImageUrl());
        dto.setCategory(issue.getCategory());
        dto.setStatus(issue.getStatus());
        dto.setCreatedAt(issue.getCreatedAt());
        return dto;
    }
}