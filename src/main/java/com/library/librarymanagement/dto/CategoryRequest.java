package com.library.librarymanagement.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class CategoryRequest {
    @NotBlank(message = "Category name is required")
    private String name;

    private String description;
}