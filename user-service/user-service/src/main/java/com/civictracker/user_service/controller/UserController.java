package com.civictracker.user_service.controller;

import com.civictracker.user_service.dto.UserResponseDTO;
import com.civictracker.user_service.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "http://localhost:3000") 
public class UserController {

    private final UserRepository userRepository;

    // 2. ADD THIS CONSTRUCTOR
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // This endpoint will be called by other services (like the Notification Service)
    // It is NOT for end-users.
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable UUID userId) {
        return userRepository.findById(userId)
                .map(user -> {
                    UserResponseDTO dto = new UserResponseDTO();
                    dto.setId(user.getId());
                    dto.setName(user.getName());
                    dto.setEmail(user.getEmail());
                    dto.setCreatedAt(user.getCreatedAt());
                    return ResponseEntity.ok(dto);
                })
                .orElse(ResponseEntity.notFound().build());
    }
}