package com.library.librarymanagement.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
public class BookRequest {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "ISBN is required")
    private String isbn;

    @NotNull(message = "Author ID is required to assign a book.")
    private Long authorId;

    @NotNull(message = "Category ID is required")
    private Long categoryId;
}