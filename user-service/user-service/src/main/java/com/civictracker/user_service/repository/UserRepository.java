package com.civictracker.user_service.repository;

import com.civictracker.user_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    // We need a way to find a user by their email for login and to check for duplicates
    Optional<User> findByEmail(String email);
}