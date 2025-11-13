package com.library.librarymanagement.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class AuthorRequest {
    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    private String bio;
}