package com.library.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AuthorResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String bio;

    private LocalDateTime createdAt;
    private String createdBy;
}