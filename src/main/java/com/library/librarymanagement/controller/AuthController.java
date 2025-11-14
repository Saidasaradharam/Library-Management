package com.library.librarymanagement.controller;

import com.library.librarymanagement.dto.AuthRequest;
import com.library.librarymanagement.entity.User;
import com.library.librarymanagement.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    // Endpoint for User registration
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody AuthRequest request) {
        User user = authService.registerNewUser(request);
        return new ResponseEntity<>("User registered successfully as " + user.getRole(), HttpStatus.CREATED);
    }

    // Login endpoint placeholder
    @PostMapping("/login")
    public ResponseEntity<AuthService.TokenResponse> loginUser(@Valid @RequestBody AuthRequest request) {

        AuthService.TokenResponse tokens = authService.login(request);
        return ResponseEntity.ok(tokens);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthService.TokenResponse> refresh(@RequestBody String refreshToken) {

        // Pass the token to the service for validation and renewal
        AuthService.TokenResponse newTokens = authService.refreshToken(refreshToken);

        return ResponseEntity.ok(newTokens);
    }

}