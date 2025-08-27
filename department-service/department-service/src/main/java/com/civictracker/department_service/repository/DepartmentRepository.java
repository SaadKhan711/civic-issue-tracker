package com.civictracker.department_service.repository;

import com.civictracker.department_service.model.Category;
import com.civictracker.department_service.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, UUID> {
    Optional<Department> findByCategory(Category category);
}