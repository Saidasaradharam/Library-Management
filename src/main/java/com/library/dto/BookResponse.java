package com.library.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class BookResponse {
    private Long id;
    private String title;
    private String isbn;

    // Embedded Author details
    private String authorName;
    private Long authorId;

    private LocalDateTime createdAt;
    private String createdBy;
}