package com.library.librarymanagement.dto;

import lombok.Data;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;

@Data
public class AuthRequest {
    @NotBlank(message = "Username/Email is required")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
}