package com.civictracker.user_service.controller;

import com.civictracker.user_service.dto.AuthRequest;
import com.civictracker.user_service.dto.AuthResponse;
import com.civictracker.user_service.dto.UserRegistrationRequest;
import com.civictracker.user_service.dto.UserResponseDTO;
import com.civictracker.user_service.service.JwtService;
import com.civictracker.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000") 
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager; // <-- NEW

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@RequestBody UserRegistrationRequest request) {
        UserResponseDTO registeredUser = userService.registerUser(request);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    // NEW LOGIN ENDPOINT
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        // This will use our custom UserDetailsService and PasswordEncoder to validate
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        // If authentication is successful, generate a token
        if (authentication.isAuthenticated()) {
            UserDetails userDetails = userService.loadUserByUsername(request.getEmail());
            String token = jwtService.generateToken(userDetails);
            return ResponseEntity.ok(new AuthResponse(token));
        } else {
            // This part is rarely reached because authenticate() throws an exception on failure
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}