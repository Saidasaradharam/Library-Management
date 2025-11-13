package com.library.librarymanagement.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class CategoryResponse {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private String createdBy;
}