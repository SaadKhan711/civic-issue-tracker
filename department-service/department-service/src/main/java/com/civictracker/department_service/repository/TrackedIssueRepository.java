package com.civictracker.department_service.repository;

import com.civictracker.department_service.model.TrackedIssue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface TrackedIssueRepository extends JpaRepository<TrackedIssue, UUID> {
}