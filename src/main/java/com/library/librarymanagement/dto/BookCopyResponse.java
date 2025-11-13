package com.library.librarymanagement.dto;

import lombok.Data;
import com.library.librarymanagement.entity.CopyStatus; // Access the inner enum
import java.time.LocalDateTime;

@Data
public class BookCopyResponse {
    private Long id;
    private Long bookId;
    private String bookTitle;
    private String inventoryNumber;
    private CopyStatus status;
    private LocalDateTime createdAt;
}